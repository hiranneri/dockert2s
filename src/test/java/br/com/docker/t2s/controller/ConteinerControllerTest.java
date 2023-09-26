package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.http.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.http.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.http.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.service.abstracao.ConteinerService;
import br.com.docker.t2s.utils.ConteinerCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class ConteinerControllerTest {

    @InjectMocks
    private ConteinerController conteinerController;
    @Mock
    private ConteinerService conteinerService;

    @BeforeEach
    void setUp() {
        PageImpl<ConteinerResponseDTO> clientePage = new PageImpl<>(List.of(ConteinerCreator.createConteinerResponseAtivo()));
        ConteinerResponseDTO conteinerResponseValido = ConteinerCreator.createConteinerResponseAtivo();
        ConteinerResponseDTO conteinerResponseEditado = ConteinerCreator.createConteinerResponseEditadoAtivo();
        ConteinerResponseDTO conteinerResponseDesativado = ConteinerCreator.createConteinerResponseInativo();

        BDDMockito.when(conteinerService.criar(ArgumentMatchers.any(ConteinerPostRequestDTO.class))).thenReturn(conteinerResponseValido);
        BDDMockito.when(conteinerService.buscarTodos()).thenReturn(List.of(conteinerResponseValido));
        BDDMockito.when(conteinerService.buscarTodos(ArgumentMatchers.any())).thenReturn(clientePage);
        BDDMockito.when(conteinerService.buscarPeloId(ArgumentMatchers.anyLong())).thenReturn(conteinerResponseValido);
        BDDMockito.when(conteinerService.buscarPeloNumero(ArgumentMatchers.anyString())).thenReturn(conteinerResponseValido);
        BDDMockito.when(conteinerService.editar(ArgumentMatchers.any(ConteinerPutRequestDTO.class))).thenReturn(conteinerResponseEditado);
        BDDMockito.when(conteinerService.deletar(ArgumentMatchers.anyLong())).thenReturn(conteinerResponseDesativado);

    }

    @Test
    void criarConteiner() {
        ConteinerPostRequestDTO conteinerPostRequestDTO = ConteinerCreator.createConteinerPostRequestValido();
        ResponseEntity<ConteinerResponseDTO> conteinerResponseSalvo = conteinerController.criarConteiner(conteinerPostRequestDTO);

        Assertions.assertNotNull(conteinerResponseSalvo);
        Assertions.assertEquals(conteinerPostRequestDTO.getCliente(), Objects.requireNonNull(conteinerResponseSalvo.getBody()).getCliente());
        Assertions.assertEquals(conteinerPostRequestDTO.getNumero(), Objects.requireNonNull(conteinerResponseSalvo.getBody().getNumeroConteiner()));
        Assertions.assertEquals(HttpStatus.CREATED, conteinerResponseSalvo.getStatusCode());
    }

    @Test
    void buscarConteineres() {
        ResponseEntity<List<ConteinerResponseDTO>> conteineresLocalizados = conteinerController.buscarConteineres();

        Assertions.assertNotNull(conteineresLocalizados);
        Assertions.assertEquals(HttpStatus.OK, conteineresLocalizados.getStatusCode());
    }

    @Test
    void buscarConteinerPeloId() {
        ResponseEntity<ConteinerResponseDTO> conteinerLocalizado = conteinerController.buscarConteinerPeloId(1L);

        Assertions.assertNotNull(conteinerLocalizado);
        Assertions.assertNotNull(conteinerLocalizado.getBody());
        Assertions.assertNotNull(conteinerLocalizado.getBody().getCliente());
        Assertions.assertEquals(Status.ATIVO,conteinerLocalizado.getBody().getStatus());

        Assertions.assertEquals(HttpStatus.OK, conteinerLocalizado.getStatusCode());
    }

    @Test
    void buscarConteinerPeloNumero() {
        String numero = ConteinerCreator.createConteinerPostRequestValido().getNumero();
        ResponseEntity<ConteinerResponseDTO> conteinerLocalizado = conteinerController.buscarConteinerPeloNome(numero);

        Assertions.assertNotNull(conteinerLocalizado);
        Assertions.assertEquals(numero, Objects.requireNonNull(conteinerLocalizado.getBody()).getNumeroConteiner());
        Assertions.assertEquals(Status.ATIVO, Objects.requireNonNull(conteinerLocalizado.getBody()).getStatus());
        Assertions.assertEquals(HttpStatus.OK, conteinerLocalizado.getStatusCode());
    }

    @Test
    void editarConteiner() {
        ConteinerPutRequestDTO conteinerASerEditadoRequest = ConteinerCreator.createConteinerPutRequestValido();

        ResponseEntity<ConteinerResponseDTO> conteinerAlterado = conteinerController.editarConteiner(conteinerASerEditadoRequest);
        ConteinerResponseDTO ConteinerResponseDTO = conteinerAlterado.getBody();

        Assertions.assertNotNull(conteinerAlterado);
        Assertions.assertNotNull(ConteinerResponseDTO);
        Assertions.assertEquals(ConteinerResponseDTO.getNumeroConteiner(), conteinerASerEditadoRequest.getNumero());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, conteinerAlterado.getStatusCode());
    }

    @Test
    void excluirConteiner() {
        ConteinerPostRequestDTO conteinerASerDesativadoRequest = ConteinerCreator.createConteinerPostRequestValido();

        ResponseEntity<ConteinerResponseDTO> conteinerDesativado = conteinerController.excluirConteiner(conteinerASerDesativadoRequest.getId());

        Assertions.assertNotNull(conteinerDesativado);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, conteinerDesativado.getStatusCode());
    }
}