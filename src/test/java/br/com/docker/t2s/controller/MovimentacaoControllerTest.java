package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.service.interfaces.MovimentacaoService;
import br.com.docker.t2s.utils.MovimentacaoCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MovimentacaoControllerTest {

    @InjectMocks
    private MovimentacaoController movimentacaoController;
    @Mock
    private MovimentacaoService movimentacaoService;

    @BeforeEach
    void setUp() {
        MovimentacaoResponseDTO movimentacaoResponseValido = MovimentacaoCreator.createMovimentacaoResponseAtivo();
        MovimentacaoResponseDTO movimentacaoResponseEditado = MovimentacaoCreator.createMovimentacaoResponseEditadoAtivo();
        MovimentacaoResponseDTO movimentacaoResponseDesativado = MovimentacaoCreator.createMovimentacaoResponseInativo();

        BDDMockito.when(movimentacaoService.criar(ArgumentMatchers.any(MovimentacaoPostRequestDTO.class))).thenReturn(movimentacaoResponseValido);
        BDDMockito.when(movimentacaoService.editarMovimentacao(ArgumentMatchers.any(MovimentacaoPutRequestDTO.class))).thenReturn(movimentacaoResponseEditado);
        BDDMockito.when(movimentacaoService.deletarMovimentacao(ArgumentMatchers.anyLong())).thenReturn(movimentacaoResponseDesativado);

    }

    @Test
    void criarMovimentacao_RetornaUmaMovimentacao_QuandoSucedido() {
        MovimentacaoPostRequestDTO movimentacaoPostRequestDTO = MovimentacaoCreator.createMovimentacaoRequestValido();
        ResponseEntity<MovimentacaoResponseDTO> movimentacaoResponseSalvo = movimentacaoController.criarMovimentacao(movimentacaoPostRequestDTO);

        Assertions.assertNotNull(movimentacaoResponseSalvo);
        Assertions.assertNotNull(movimentacaoPostRequestDTO);

        Assertions.assertNotNull(movimentacaoResponseSalvo.getBody().getDataHoraInicio());

        Assertions.assertEquals(movimentacaoPostRequestDTO.getTipoMovimentacao(),  movimentacaoResponseSalvo.getBody().getTipoMovimentacao());

    }

    @Test
    void editarMovimentacao_RetornaUmaMovimentacaoAlterada_QuandoSucedido() {
        MovimentacaoPutRequestDTO movimentacaoASerEditadoRequest = MovimentacaoCreator.createMovimentacaoPutRequestValido();

        ResponseEntity<MovimentacaoResponseDTO> movimentacaoAlterado = movimentacaoController
                .editarMovimentacao(1L,movimentacaoASerEditadoRequest);

        MovimentacaoResponseDTO movimentacaoResponseDTO = movimentacaoAlterado.getBody();

        Assertions.assertNotNull(movimentacaoAlterado);
        Assertions.assertNotNull(movimentacaoResponseDTO);
        Assertions.assertEquals(movimentacaoResponseDTO.getTipoMovimentacao(), movimentacaoASerEditadoRequest.getTipoMovimentacao());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, movimentacaoAlterado.getStatusCode());
    }

    @Test
    void excluirMovimentacao_NaoRetornaUmaMovimentacao_QuandoSucedido() {
        MovimentacaoPutRequestDTO movimentacaoASerDesativadoRequest = MovimentacaoCreator.createMovimentacaoPutRequestValido();

        ResponseEntity<Void> MovimentacaoDesativado = movimentacaoController
                .excluirMovimentacao(movimentacaoASerDesativadoRequest.getId());

        Assertions.assertNotNull(MovimentacaoDesativado);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, MovimentacaoDesativado.getStatusCode());
    }
}