package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClienteResponseDTO;
import br.com.docker.t2s.service.interfaces.ClienteService;
import br.com.docker.t2s.utils.ClienteCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;
    @Mock
    private ClienteService clienteService;


    @BeforeEach
    void setup(){

        PageImpl<ClienteResponseDTO> clientePage = new PageImpl<>(List.of(ClienteCreator.createClienteResponseAtivo()));
        ClienteResponseDTO clienteResponseValido = ClienteCreator.createClienteResponseAtivo();
        ClienteResponseDTO clienteResponseEditado = ClienteCreator.createClienteResponseEditadoAtivo();
        ClienteResponseDTO clienteResponseDesativado = ClienteCreator.createClienteResponseInativo();

        BDDMockito.when(clienteService.criar(ArgumentMatchers.any(ClientePostRequestDTO.class))).thenReturn(clienteResponseValido);
        BDDMockito.when(clienteService.buscarTodos()).thenReturn(List.of(clienteResponseValido));
        BDDMockito.when(clienteService.buscarTodos(ArgumentMatchers.any())).thenReturn(clientePage);
        BDDMockito.when(clienteService.buscarPeloID(ArgumentMatchers.anyLong())).thenReturn(clienteResponseValido);
        BDDMockito.when(clienteService.buscarPeloNome(ArgumentMatchers.anyString())).thenReturn(clienteResponseValido);
        BDDMockito.when(clienteService.editar(ArgumentMatchers.any(ClientePutRequestDTO.class))).thenReturn(clienteResponseEditado);
        BDDMockito.when(clienteService.desativar(ArgumentMatchers.anyLong())).thenReturn(clienteResponseDesativado);

    }

    @Test
    void buscarClientes_RetornaListaClienteResponse_QuandoSucedido(){
        ClienteResponseDTO clienteDTO = ClienteCreator.createClienteResponseAtivo();
        Page<ClienteResponseDTO> clientesLocalizados = clienteController.buscarClientes(null).getBody();

        Assertions.assertNotNull(clientesLocalizados);
        Assertions.assertEquals(1, clientesLocalizados.getTotalElements());
        Assertions.assertEquals(clienteDTO.getNome(), clientesLocalizados.toList().get(0).getNome());

    }

    @Test
    void criarCliente_PersisteERetornaUmCliente_QuandoSucedido(){
        ClientePostRequestDTO clientePostRequestDTO = ClienteCreator.createClientePostRequestValido();
        ResponseEntity<ClienteResponseDTO> clienteResponseSalvo = clienteController.criarCliente(clientePostRequestDTO);

        Assertions.assertNotNull(clienteResponseSalvo);
        Assertions.assertEquals(clientePostRequestDTO.getNome(), Objects.requireNonNull(clienteResponseSalvo.getBody()).getNome());
        Assertions.assertEquals(HttpStatus.CREATED, clienteResponseSalvo.getStatusCode());
    }

    @Test
    void buscarClientes_RetornaListaClientes_QuandoSucedido(){
        ResponseEntity<List<ClienteResponseDTO>> clientesLocalizados = clienteController.buscarClientes();

        Assertions.assertNotNull(clientesLocalizados);
        Assertions.assertEquals(HttpStatus.OK, clientesLocalizados.getStatusCode());
    }

    @Test
    void buscarClientPeloId_RetornaUmCliente_QuandoSucedido(){
        ResponseEntity<ClienteResponseDTO> clienteLocalizado = clienteController.buscarClientPeloId(1L);

        Assertions.assertNotNull(clienteLocalizado);
        Assertions.assertNotNull(clienteLocalizado.getBody());
        Assertions.assertNotNull(clienteLocalizado.getBody().getNome());
        Assertions.assertEquals(HttpStatus.OK, clienteLocalizado.getStatusCode());

    }

    @Test
    void buscarClientePeloNome_RetornaUmCliente_QuandoSucedido(){
        String nomeCliente = ClienteCreator.createClienteRequestValido().getNome();
        ResponseEntity<ClienteResponseDTO> clienteLocalizado = clienteController.buscarClientePeloNome(nomeCliente);

        Assertions.assertNotNull(clienteLocalizado);
        Assertions.assertEquals(nomeCliente, Objects.requireNonNull(clienteLocalizado.getBody()).getNome());
        Assertions.assertEquals(HttpStatus.OK, clienteLocalizado.getStatusCode());

    }

    @Test
    void editarCliente_RetornaUmClienteModificado_QuandoSucedido(){
        ClientePutRequestDTO clienteASerEditadoRequest = ClienteCreator.createClienteRequestEditado();
        Long id = clienteASerEditadoRequest.getId();

        ResponseEntity<ClienteResponseDTO> clienteAlterado = clienteController.editarCliente(id, clienteASerEditadoRequest);
        ClienteResponseDTO clienteResponseDTO = clienteAlterado.getBody();

        Assertions.assertNotNull(clienteAlterado);
        Assertions.assertNotNull(clienteResponseDTO);
        Assertions.assertEquals(clienteResponseDTO.getNome(), clienteASerEditadoRequest.getNome());
        Assertions.assertEquals(HttpStatus.OK, clienteAlterado.getStatusCode());

    }

    @Test
    public void excluirCliente_DesativaUmCliente_QuandoSucedido(){
        ClientePutRequestDTO clienteASerDesativadoRequest = ClienteCreator.createClienteRequestValido();

        ResponseEntity<Void> clienteDesativado = clienteController.excluirCliente(clienteASerDesativadoRequest.getId());

        Assertions.assertNotNull(clienteDesativado);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, clienteDesativado.getStatusCode());

    }


}