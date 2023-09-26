package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.http.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClienteResponseDTO;
import br.com.docker.t2s.exceptions.BadRequestException;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.repository.ClienteRepository;
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
        BDDMockito.when(clienteRepository.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(clienteAtivo));

        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteDesativado); // desativar
    }

    @Test
    void criarCliente_PersisteERetornaUmCliente_QuandoSucedido(){
        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteAtivo);
        ClientePostRequestDTO clientePutRequestDTO = ClienteCreator.createClientePostRequestValido();
        ClienteResponseDTO clienteResponseSalvo = clienteService.criar(clientePutRequestDTO);

        Assertions.assertNotNull(clienteResponseSalvo);
        Assertions.assertEquals(clientePutRequestDTO.getNome(), Objects.requireNonNull(clienteResponseSalvo).getNome());
        Assertions.assertEquals(Status.ATIVO, Objects.requireNonNull(clienteResponseSalvo.getStatus()));

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
        Assertions.assertEquals(Status.ATIVO,clienteLocalizado.getStatus());

    }

    @Test
    void buscarClientePeloNome_RetornaUmCliente_QuandoSucedido(){
        String nomeCliente = ClienteCreator.createClienteRequestValido().getNome();
        ClienteResponseDTO clienteLocalizado = clienteService.buscarPeloNome(nomeCliente);

        Assertions.assertNotNull(clienteLocalizado);
        Assertions.assertEquals(nomeCliente, Objects.requireNonNull(clienteLocalizado.getNome()));
        Assertions.assertEquals(Status.ATIVO, Objects.requireNonNull(clienteLocalizado.getStatus()));


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
        BDDMockito.when(clienteRepository.findById(50L)).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class,()-> clienteService.buscarPeloIDOuLancarExcecaoNaoEncontrado(50L));

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
        BDDMockito.when(clienteRepository.findByNome(ArgumentMatchers.anyString())).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,
                ()->
                        clienteService.buscarPeloNome(ClienteCreator.createClienteRequestComNomeInexistente().getNome())
        );

    }

    @Test
    public void buscarClienteCompleto_RetornaUmaExcecao_QuandoClienteInexistente(){
        BDDMockito.when(clienteRepository.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class,
                ()-> clienteService.buscarClienteCompleto(
                        ClienteCreator.createClienteRequestComNomeInexistente().getNome()));

    }

}