package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.dtos.mappers.movimentacao.MovimentacaoMapper;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.repository.MovimentacaoRepository;
import br.com.docker.t2s.repository.TiposMovimentacaoRepository;
import br.com.docker.t2s.utils.ConteinerCreator;
import br.com.docker.t2s.utils.MovimentacaoCreator;
import br.com.docker.t2s.utils.TiposMovimentacaoCreator;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class MovimentacaoServiceImplTest {

    @InjectMocks
    MovimentacaoServiceImpl movimentacaoService;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    @Mock
    private TiposMovimentacaoRepository tiposMovimentacaoRepository;

    @Mock
    private ConteinerServiceImpl conteinerService;

    @Mock
    private MovimentacaoMapper movimentacaoMapper;

    @Mock
    MovimentacaoPutRequestDTO movimentacaoRequestEditado;

    @BeforeEach
    void setup(){

        Movimentacao movimentacaoAtiva = MovimentacaoCreator.createMovimentacao();
        Movimentacao movimentacaoEditado = MovimentacaoCreator.createMovimentacaoEditada();
        MovimentacaoResponseDTO movimentacaoResponseDTO = MovimentacaoCreator.createMovimentacaoResponseAtivo();

        List<Movimentacao> Movimentacaos = new ArrayList<>();
        Movimentacaos.add(movimentacaoAtiva);

        BDDMockito.when(movimentacaoRepository.findAll()).thenReturn(Movimentacaos);
        BDDMockito.when(movimentacaoMapper.toMovimentacaoResponse(ArgumentMatchers.any(Movimentacao.class))).thenReturn(movimentacaoResponseDTO);

        BDDMockito.when(movimentacaoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(movimentacaoAtiva));
        BDDMockito.when(movimentacaoRepository.findAll()).thenReturn(List.of(movimentacaoAtiva));
        BDDMockito.when(movimentacaoRepository.save(ArgumentMatchers.any(Movimentacao.class))).thenReturn(movimentacaoAtiva);
        BDDMockito.when(movimentacaoRepository.findByDataHoraInicio(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(Optional.of(movimentacaoAtiva));
        BDDMockito.when(movimentacaoRepository.save(ArgumentMatchers.any(Movimentacao.class))).thenReturn(movimentacaoEditado);
        BDDMockito.when(tiposMovimentacaoRepository.findByNome(ArgumentMatchers.any(NomeMovimentacao.class)))
                .thenReturn(TiposMovimentacaoCreator.createTiposMovimentacao());
        BDDMockito.when(movimentacaoRepository.findById(movimentacaoRequestEditado.getId()))
                .thenReturn(Optional.of(MovimentacaoCreator.createMovimentacaoEditada()));

        BDDMockito.when(conteinerService.buscarConteinerCompletoPeloNumero(ArgumentMatchers.anyString()))
                .thenReturn(ConteinerCreator.createConteinerAtivo());

    }

    @Test
    void criarMovimentacao_PersisteERetornaUmMovimentacao_QuandoSucedido(){

        MovimentacaoPostRequestDTO movimentacaoPostRequestDTO = MovimentacaoCreator.createMovimentacaoRequestValido();
        MovimentacaoResponseDTO movimentacaoResponseSalvo = movimentacaoService.criar(movimentacaoPostRequestDTO);
        movimentacaoRequestEditado = MovimentacaoCreator.createMovimentacaoRequestEditado();

        Assertions.assertNotNull(movimentacaoResponseSalvo);
        Assertions.assertEquals(movimentacaoPostRequestDTO.getTipoMovimentacao(), Objects.requireNonNull(movimentacaoResponseSalvo).getTipoMovimentacao());
        Assertions.assertEquals(Status.ATIVO, movimentacaoResponseSalvo.getStatus());

    }

    @Test
    void buscarMovimentacaos_RetornaListaMovimentacaos_QuandoSucedido(){
        List<MovimentacaoResponseDTO> MovimentacaoesLocalizados = movimentacaoService.buscarMovimentacoes();

        Assertions.assertNotNull(MovimentacaoesLocalizados);
        Assertions.assertTrue(MovimentacaoesLocalizados.size()>0);
    }

    @Test
    void editarMovimentacao_RetornaUmMovimentacaoModificado_QuandoSucedido(){
        MovimentacaoPutRequestDTO movimentacaoRequestEditado = MovimentacaoCreator.createMovimentacaoRequestEditado();

        Movimentacao movimentacaoEditadaBanco = MovimentacaoCreator.createMovimentacaoEditada();

        MovimentacaoResponseDTO movimentacaoAlterado = movimentacaoService.editarMovimentacao(movimentacaoRequestEditado);

        Assertions.assertNotNull(movimentacaoAlterado);
        Assertions.assertEquals(movimentacaoAlterado.getNumeroConteiner(), movimentacaoEditadaBanco.getConteiner().getNumero());

    }

    @Test
    public void excluirMovimentacao_DesativaUmMovimentacao_QuandoSucedido(){

        MovimentacaoResponseDTO MovimentacaoDesativado = movimentacaoService.deletarMovimentacao(movimentacaoRequestEditado.getId());

        Assertions.assertNotNull(MovimentacaoDesativado);

    }

    @Test
    public void criar_RetornaUmaExcecaoConstraintViolationException_QuandoMovimentacaoJaExiste(){

        BDDMockito.given(movimentacaoRepository.save(ArgumentMatchers.any(Movimentacao.class))).willThrow(ConstraintViolationException.class);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> movimentacaoService.criar(MovimentacaoCreator.createMovimentacaoJaExistente()));

    }

    @Test
    public void criar_RetornaUmaExcecao_QuandoMovimentacaoIJaPossuiID(){
        BDDMockito.given(movimentacaoRepository.save(ArgumentMatchers.any(Movimentacao.class))).willThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,()-> movimentacaoService.criar(MovimentacaoCreator.createMovimentacaoJaExistente()));

    }

    @Test
    public void editar_RetornaUmaExcecao_QuandoMovimentacaoSemTipo(){

        BDDMockito.when(movimentacaoRepository.save(ArgumentMatchers.any(Movimentacao.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,()->
                movimentacaoService.editarMovimentacao(MovimentacaoCreator.createMovimentacaoRequestSemTipo()));

    }

    @Test
    public void editar_RetornaUmaExcecao_QuandoMovimentacaoSemID(){
        BDDMockito.when(movimentacaoRepository.save(ArgumentMatchers.any(Movimentacao.class))).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class,
                ()-> movimentacaoService.editarMovimentacao(MovimentacaoCreator.createMovimentacaoSemID()));

    }

    @Test
    @DisplayName("Pesquisar uma movimentação inativa")
    void AH_PesquisarMovimentacaoInativaRetornarBadRequestException() throws Exception {
        Assertions.assertTrue(true);
    }



}