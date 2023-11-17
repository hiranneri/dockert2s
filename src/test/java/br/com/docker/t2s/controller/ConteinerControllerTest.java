package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.service.interfaces.ConteinerService;
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
        Assertions.assertEquals(conteinerPostRequestDTO.getNomeCliente(), Objects.requireNonNull(conteinerResponseSalvo.getBody()).getNomeCliente());
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
        Assertions.assertNotNull(conteinerLocalizado.getBody().getNomeCliente());
        Assertions.assertEquals(HttpStatus.OK, conteinerLocalizado.getStatusCode());
    }

    @Test
    void buscarConteinerPeloNumero() {
        String numero = ConteinerCreator.createConteinerPostRequestValido().getNumero();
        ResponseEntity<ConteinerResponseDTO> conteinerLocalizado = conteinerController.buscarConteinerPeloNome(numero);

        Assertions.assertNotNull(conteinerLocalizado);
        Assertions.assertEquals(numero, Objects.requireNonNull(conteinerLocalizado.getBody()).getNumeroConteiner());
        Assertions.assertEquals(HttpStatus.OK, conteinerLocalizado.getStatusCode());
    }

    @Test
    void editarConteiner() {
        ConteinerPutRequestDTO conteinerASerEditadoRequest = ConteinerCreator.createConteinerPutRequestValido();

        ResponseEntity<ConteinerResponseDTO> conteinerAlterado = conteinerController.editarConteiner(1L,conteinerASerEditadoRequest);
        ConteinerResponseDTO conteinerResponseDTO = conteinerAlterado.getBody();

        Assertions.assertNotNull(conteinerAlterado);
        Assertions.assertNotNull(conteinerResponseDTO);
        Assertions.assertEquals(conteinerResponseDTO.getNumeroConteiner(), conteinerASerEditadoRequest.getNumero());
        Assertions.assertEquals(HttpStatus.OK, conteinerAlterado.getStatusCode());
    }

    @Test
    void excluirConteiner() {
        ConteinerPostRequestDTO conteinerASerDesativadoRequest = ConteinerCreator.createConteinerPostRequestValido();

        ResponseEntity<Void> conteinerDesativado = conteinerController.excluirConteiner(conteinerASerDesativadoRequest.getId());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, conteinerDesativado.getStatusCode());
    }
}