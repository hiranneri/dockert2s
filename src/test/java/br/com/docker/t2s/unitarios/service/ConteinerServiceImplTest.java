package br.com.docker.t2s.unitarios.service;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.controller.dtos.mappers.conteiner.ConteinerMapper;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.repository.ConteinerRepository;
import br.com.docker.t2s.service.ConteinerServiceImpl;
import br.com.docker.t2s.service.interfaces.ClienteService;
import br.com.docker.t2s.utils.ClienteCreator;
import br.com.docker.t2s.utils.ConteinerCreator;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ConteinerServiceImplTest {

    @InjectMocks
    private ConteinerServiceImpl conteinerService;
    @Mock
    private ConteinerRepository conteinerRepository;
    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setup(){

        Conteiner conteinerAtivo = ConteinerCreator.createConteinerAtivo();
        Conteiner conteinerEditado = ConteinerCreator.createConteinerEditadoAtivo();
        Conteiner conteinerDesativado = ConteinerCreator.createConteinerInativo();


        List<Conteiner> conteiners = new ArrayList<>();
        conteiners.add(conteinerAtivo);

        BDDMockito.when(conteinerRepository.findAll()).thenReturn(conteiners);
        BDDMockito.when(clienteService.buscarClienteCompleto(ArgumentMatchers.anyString())).thenReturn(ClienteCreator.createClienteAtivo());

        BDDMockito.when(conteinerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(conteinerAtivo));
        BDDMockito.when(conteinerRepository.findAll()).thenReturn(List.of(conteinerAtivo));
        BDDMockito.when(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).thenReturn(conteinerAtivo);
        BDDMockito.when(conteinerRepository.findByNumeroAndStatus(ArgumentMatchers.anyString(),ArgumentMatchers.anyBoolean())).thenReturn(Optional.of(conteinerAtivo));
        BDDMockito.when(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).thenReturn(conteinerEditado);

        BDDMockito.when(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).thenReturn(conteinerDesativado); // desativar
    }

    @Test
    void criarconteiner_PersisteERetornaUmconteiner_QuandoSucedido(){
        BDDMockito.when(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).thenReturn(ConteinerCreator.createConteinerAtivo());

        ConteinerPostRequestDTO conteinerPostRequestDTO = ConteinerCreator.createConteinerPostRequestValido2();
        ConteinerResponseDTO conteinerResponseSalvo = conteinerService.criar(conteinerPostRequestDTO);

        Assertions.assertNotNull(conteinerResponseSalvo);
        Assertions.assertEquals(conteinerPostRequestDTO.getNumero(), Objects.requireNonNull(conteinerResponseSalvo).getNumeroConteiner());
        Assertions.assertEquals("Ativo", conteinerResponseSalvo.getStatus());

    }

    @Test
    void buscarconteiners_RetornaListaconteiners_QuandoSucedido(){
        List<ConteinerResponseDTO> conteineresLocalizados = conteinerService.buscarTodos();

        Assertions.assertNotNull(conteineresLocalizados);
        Assertions.assertTrue(conteineresLocalizados.size()>0);
    }

    @Test
    void buscarClientPeloId_RetornaUmconteiner_QuandoSucedido(){
        ConteinerResponseDTO conteinerLocalizado = conteinerService.buscarPeloId(1L);

        Assertions.assertNotNull(conteinerLocalizado);
        Assertions.assertNotNull(conteinerLocalizado.getNumeroConteiner());
        Assertions.assertEquals("Ativo", conteinerLocalizado.getStatus());

    }

    @Test
    void buscarconteinerPeloNome_RetornaUmconteiner_QuandoSucedido2(){
        String numero = ConteinerCreator.createConteinerPostRequestValido2().getNumero();
        ConteinerResponseDTO conteinerLocalizado = conteinerService.buscarPeloNumero(numero);

        Assertions.assertNotNull(conteinerLocalizado);
        Assertions.assertEquals(numero, Objects.requireNonNull(conteinerLocalizado.getNumeroConteiner()));
        Assertions.assertEquals("Ativo", conteinerLocalizado.getStatus());


    }

    @Test
    void editarconteiner_RetornaUmconteinerModificado_QuandoSucedido(){
        Conteiner conteinerResponseEditadoAtivo = ConteinerCreator.createConteinerEditadoAtivo();
        ConteinerPutRequestDTO conteinerRequestEditado = ConteinerCreator.createConteinerRequestEditado();

        BDDMockito.when(conteinerRepository.findById(conteinerRequestEditado.getId()))
                .thenReturn(Optional.of(conteinerResponseEditadoAtivo));

        ConteinerResponseDTO conteinerAlterado = conteinerService.editar(conteinerRequestEditado);

        Assertions.assertNotNull(conteinerAlterado);
        Assertions.assertEquals(conteinerAlterado.getNumeroConteiner(), conteinerRequestEditado.getNumero());

    }

    @Test
    public void excluirconteiner_DesativaUmconteiner_QuandoSucedido(){
        ConteinerPutRequestDTO conteinerRequestEditado = ConteinerCreator.createConteinerRequestEditado();

        ConteinerResponseDTO conteinerDesativado = conteinerService.deletar(conteinerRequestEditado.getId());

        Assertions.assertNotNull(conteinerDesativado);

    }

    @Test
    public void buscarPeloIDOuLancarExcecaoNaoEncontrado_RetornaUmaExcecao_QuandoconteinerInvalido(){
        BDDMockito.when(conteinerRepository.findById(50L)).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class,()-> conteinerService.buscarPeloId(50L));

    }

    @Test
    public void criar_RetornaUmaExcecaoConstraintViolationException_QuandoConteinerJaExiste(){

        BDDMockito.given(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).willThrow(ConstraintViolationException.class);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> conteinerService.criar(ConteinerCreator.createConteinerJaExistente()));

    }

    @Test
    public void criar_RetornaUmaExcecao_QuandoconteinerIJaPossuiID(){
        BDDMockito.given(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).willThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> conteinerService.criar(ConteinerCreator.createConteinerJaExistente()));

    }

    @Test
    public void editar_RetornaUmaExcecao_QuandoconteinerSemNome(){

        BDDMockito.when(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> conteinerService.editar(ConteinerCreator.createConteinerRequestSemNumero()));

    }

    @Test
    public void editar_RetornaUmaExcecao_QuandoconteinerSemID(){
        BDDMockito.when(conteinerRepository.save(ArgumentMatchers.any(Conteiner.class))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> conteinerService.editar(ConteinerCreator.createConteinerPutSemID()));

    }

    @Test
    public void buscarPeloNome_RetornaUmaExcecao_QuandoNomeconteinerInexistente(){
        BDDMockito.when(conteinerRepository.findByNumeroAndStatus(ArgumentMatchers.anyString(),ArgumentMatchers.anyBoolean() )).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,
                ()->
                        conteinerService.buscarPeloNumero(ConteinerCreator.createConteinerRequestComNomeInexistente().getNumero())
        );

    }

    @Test
    public void buscarconteinerCompleto_RetornaUmaExcecao_QuandoconteinerInexistente(){
        BDDMockito.when(conteinerRepository.findByNumeroAndStatus(ArgumentMatchers.anyString(),ArgumentMatchers.anyBoolean() )).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class,
                ()-> conteinerService.buscarConteinerCompletoPeloNumero(
                        ConteinerCreator.createConteinerRequestComNomeInexistente().getNumero()));

    }

}