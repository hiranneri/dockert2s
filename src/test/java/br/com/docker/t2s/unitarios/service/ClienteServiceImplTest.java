package br.com.docker.t2s.unitarios.service;

import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClienteResponseDTO;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.repository.ClienteRepository;
import br.com.docker.t2s.service.ClienteServiceImpl;
import br.com.docker.t2s.utils.ClienteCreator;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;
    @Mock
    private ClienteRepository clienteRepository;

    Cliente clienteAtivo;
    Cliente clienteEditado;
    Cliente clienteDesativado;

    @BeforeEach
    void setup(){

        clienteAtivo = ClienteCreator.createClienteAtivo();
        clienteEditado = ClienteCreator.createClienteEditadoAtivo();
        clienteDesativado = ClienteCreator.createClienteInativo();

        BDDMockito.when(clienteRepository.findAll()).thenReturn(List.of(clienteAtivo));

        BDDMockito.when(clienteRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(clienteAtivo));
        BDDMockito.when(clienteRepository.findByNomeAndStatus(ArgumentMatchers.anyString(), eq(true))).thenReturn(Optional.of(clienteAtivo));
        BDDMockito.when(clienteRepository.findByIdAndStatus(ArgumentMatchers.anyLong(), eq(true))).thenReturn(Optional.of(clienteAtivo));

        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteDesativado); // desativar
    }

    @Test
    void criarCliente_PersisteERetornaUmCliente_QuandoSucedido(){
        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteAtivo);
        ClientePostRequestDTO clientePutRequestDTO = ClienteCreator.createClientePostRequestValido();
        ClienteResponseDTO clienteResponseSalvo = clienteService.criar(clientePutRequestDTO);

        Assertions.assertNotNull(clienteResponseSalvo);
        Assertions.assertEquals(clientePutRequestDTO.getNome(), Objects.requireNonNull(clienteResponseSalvo).getNome());
        Assertions.assertEquals("Ativo",clienteResponseSalvo.getStatus());

    }

    @Test
    void buscarClientes_RetornaListaClientes_QuandoSucedido(){
        List<ClienteResponseDTO> clientesLocalizados = clienteService.buscarTodos();

        Assertions.assertNotNull(clientesLocalizados);
        Assertions.assertTrue(clientesLocalizados.size()>0);
    }

    @Test
    void buscarClientPeloId_RetornaUmCliente_QuandoSucedido(){
        ClienteResponseDTO clienteLocalizado = clienteService.buscarPeloID(1L);

        Assertions.assertNotNull(clienteLocalizado);
        Assertions.assertNotNull(clienteLocalizado);
        Assertions.assertNotNull(clienteLocalizado.getNome());

    }

    @Test
    void buscarClientePeloNome_RetornaUmCliente_QuandoSucedido(){
        String nomeCliente = ClienteCreator.createClienteRequestValido().getNome();
        ClienteResponseDTO clienteLocalizado = clienteService.buscarPeloNome(nomeCliente);

        Assertions.assertNotNull(clienteLocalizado);
        Assertions.assertEquals(nomeCliente, Objects.requireNonNull(clienteLocalizado.getNome()));


    }

    @Test
    void editarCliente_RetornaUmClienteModificado_QuandoSucedido(){
        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteEditado);
        ClientePutRequestDTO clienteASerEditadoRequest = ClienteCreator.createClienteRequestEditado();

        ClienteResponseDTO clienteAlterado = clienteService.editar(clienteASerEditadoRequest);

        Assertions.assertNotNull(clienteAlterado);
        Assertions.assertNotNull(clienteAlterado);
        Assertions.assertEquals(clienteAlterado.getNome(), clienteASerEditadoRequest.getNome());

    }

    @Test
    public void excluirCliente_DesativaUmCliente_QuandoSucedido(){
        ClientePutRequestDTO clienteASerDesativadoRequest = ClienteCreator.createClienteRequestEditado();

        ClienteResponseDTO clienteDesativado = clienteService.desativar(clienteASerDesativadoRequest.getId());

        Assertions.assertNotNull(clienteDesativado);

    }

    @Test
    public void buscarPeloIDOuLancarExcecaoNaoEncontrado_RetornaUmaExcecao_QuandoClienteInvalido(){
        Long idInexistente = 50L;
        BDDMockito.when(clienteRepository.findByIdAndStatus(ArgumentMatchers.anyLong(), eq(true))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()->
                clienteService.buscarPeloIDOuLancarExcecaoNaoEncontrado(idInexistente)
        );

    }

    @Test
    public void criar_RetornaUmaExcecaoConstraintViolationException_QuandoClienteJaExiste(){

        BDDMockito.given(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).willThrow(ConstraintViolationException.class);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> clienteService.criar(ClienteCreator.createClienteJaExistente()));

    }

    @Test
    public void criar_RetornaUmaExcecao_QuandoClienteIJaPossuiID(){
        BDDMockito.given(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).willThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> clienteService.criar(ClienteCreator.createClienteJaExistente()));

    }

    @Test
    public void editar_RetornaUmaExcecao_QuandoClienteSemNome(){
        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> clienteService.editar(ClienteCreator.createClienteRequestSemNome()));

    }

    @Test
    public void editar_RetornaUmaExcecao_QuandoClienteSemID(){
        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> clienteService.editar(ClienteCreator.createClientePutSemID()));

    }

    @Test
    public void buscarPeloNome_RetornaUmaExcecao_QuandoNomeClienteInexistente(){
        BDDMockito.when(clienteRepository.findByNomeAndStatus(ArgumentMatchers.anyString(),eq(true))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,
                ()->
                        clienteService.buscarPeloNome(ClienteCreator.createClienteRequestComNomeInexistente().getNome())
        );

    }

    @Test
    public void buscarClienteCompleto_RetornaUmaExcecao_QuandoClienteInexistente(){
        BDDMockito.when(clienteRepository.findByNomeAndStatus(ArgumentMatchers.anyString(),eq(true))).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class,
                ()-> clienteService.buscarClienteCompleto(
                        ClienteCreator.createClienteRequestComNomeInexistente().getNome()));

    }

}