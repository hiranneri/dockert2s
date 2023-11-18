package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.dtos.mappers.movimentacao.MovimentacaoMapper;
import br.com.docker.t2s.controller.dtos.movimentacao.*;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import br.com.docker.t2s.repository.MovimentacaoRepository;
import br.com.docker.t2s.repository.strategies.OrdenacaoStrategy;
import br.com.docker.t2s.repository.TiposMovimentacaoRepository;
import br.com.docker.t2s.repository.dto.RelatorioAgrupadoComSumarioDTO;
import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;
import br.com.docker.t2s.repository.dto.SumarioDTO;
import br.com.docker.t2s.service.interfaces.ConteinerService;
import br.com.docker.t2s.service.interfaces.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ConteinerService conteinerService;
    private final TiposMovimentacaoRepository tiposMovimentacaoRepository;

    @Override
    public MovimentacaoResponseDTO criar(MovimentacaoPostRequestDTO movimentacaoRequest) {
        TipoMovimentacao tipoMovimentacao = pesquisarTipoMovimentacao(movimentacaoRequest.getTipoMovimentacao());
        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoRequest.getNumeroConteiner());

        boolean movimentacaoLocalizada = movimentacaoRepository.findByTipoMovimentacaoAndConteinerAndStatus(
                tipoMovimentacao, conteiner, true).isPresent();
        if(movimentacaoLocalizada) {
            throw new BadRequestException("Já existe uma movimentação com o mesmo TIPO_MOVIMENTACAO para o mesmo CONTEINER.");
        }

        Movimentacao movimentacao = Movimentacao.builder()
                .conteiner(conteiner)
                .tipoMovimentacao(tipoMovimentacao)
                .build();

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);

        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoSalva);
    }

    private TipoMovimentacao pesquisarTipoMovimentacao(String nomeTipo) {
        NomeMovimentacao nomeMovimentacao = NomeMovimentacao.valueOf(nomeTipo);
        return tiposMovimentacaoRepository.findByNomeAndStatus(nomeMovimentacao, true)
                .orElseThrow((
                        ()-> new BadRequestException("Tipo de Movimentação não foi localizada"))
                );
    }
    @Override
    public List<MovimentacaoResponseDTO> buscarMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findAll();
        List<MovimentacaoResponseDTO> movimentacoesResponse = new ArrayList<>();
        for(Movimentacao movimentacao: movimentacoes){
            movimentacoesResponse.add(MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacao));
        }
        return movimentacoesResponse;
    }

    @Override
    public RelatorioAgrupadoComSumarioDTO gerarRelatoriosMovimentacoesAgrupadas(String campo, String ordenacao) {
        List<String> parametrosFiltro = validarParametrosFiltro(campo, ordenacao);

        campo = parametrosFiltro.get(0);
        ordenacao = parametrosFiltro.get(1);
        String filtrosBusca = campo.concat(" - ").concat(ordenacao);

        List<ResultadoRelatorioDTO> relatorioAgrupado = new OrdenacaoStrategy(movimentacaoRepository).execute(filtrosBusca);
        List<SumarioDTO> sumariosDTO = movimentacaoRepository.obterTotalAgrupadoPorTipoMovimentacao();
        return RelatorioAgrupadoComSumarioDTO.gerarRelatorio(relatorioAgrupado, sumariosDTO);
    }

    @Override
    public List<TipoMovimentacaoResponseDTO> criarTiposMovimentacao(TipoMovimentacaoListWrapper tipos) {
        List<TipoMovimentacao> tiposMovimentacao = MovimentacaoMapper.INSTANCE.toTiposMovimentacao(tipos.getTipos());

        List<TipoMovimentacao> tiposCadastrados = tiposMovimentacaoRepository.saveAll(tiposMovimentacao);

        return MovimentacaoMapper.INSTANCE.toTiposMovimentacaoResponse(tiposCadastrados);
    }

    private List<String> validarParametrosFiltro(String campo, String ordenacao){

        List<String> parametros = new ArrayList<>();

        final Map<String, String> CAMPOS_PERMITIDOS = Map.of(
                "cliente", "cliente",
                "tipoMovimentacao", "tipoMovimentacao"
        );

        final Map<String, String> ORDENACOES_PERMITADAS = Map.of(
                "ASC", "ASC",
                "DESC", "DESC"
        );

        if(!StringUtils.hasText(campo)) campo = "cliente";
        if(!StringUtils.hasText(ordenacao)) ordenacao = "ASC";

        campo = CAMPOS_PERMITIDOS.getOrDefault(campo, "cliente");
        ordenacao = ORDENACOES_PERMITADAS.getOrDefault(ordenacao, "ASC");

        parametros.add(campo);
        parametros.add(ordenacao);

        return parametros;

    }

    @Override
    public Page<MovimentacaoResponseDTO> buscarMovimentacoes(Pageable pageable) {
        List<Movimentacao> movimentacoesLocalizadas = movimentacaoRepository.findAll(pageable).getContent();
        List<MovimentacaoResponseDTO> movimentacoesResponse = new ArrayList<>();

        for(Movimentacao movimentacao: movimentacoesLocalizadas){
            MovimentacaoResponseDTO movimentacaoResponseDTO = MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacao);
            movimentacoesResponse.add(movimentacaoResponseDTO);
        }

        return new PageImpl<>(movimentacoesResponse);
    }

    @Override
    public MovimentacaoResponseDTO editarMovimentacao(MovimentacaoPutRequestDTO movimentacaoPostRequestDTO) {
        LocalDateTime dataHoraInicio = pesquisarPorIdOuLancarExcecao(movimentacaoPostRequestDTO.getId()).getDataHoraInicio();
        NomeMovimentacao nomeMovimentacao = NomeMovimentacao.valueOf(movimentacaoPostRequestDTO.getTipoMovimentacao());

        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoPostRequestDTO.getNumeroConteiner());
        TipoMovimentacao tipoMovimentacao = tiposMovimentacaoRepository.findByNomeAndStatus(nomeMovimentacao, true)
                .orElseThrow(()-> new BadRequestException("Tipo de Movimentação não localizada"));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(movimentacaoPostRequestDTO.getId());
        movimentacao.setDataHoraInicio(dataHoraInicio);
        movimentacao.setConteiner(conteiner);
        movimentacao.setTipoMovimentacao(tipoMovimentacao);
        movimentacao.setStatus(true);

        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoRepository.save(movimentacao));
    }
    @Override
    public MovimentacaoResponseDTO deletarMovimentacao(Long id) {
        Movimentacao movimentacaoLocalizada = pesquisarPorIdOuLancarExcecao(id);
        movimentacaoLocalizada.setStatus(false);
        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoRepository.save(movimentacaoLocalizada));
    }

    @Override
    public MovimentacaoPatchResponseDTO finalizar(MovimentacaoPatchRequestDTO movimentacaoPatch) {
        TipoMovimentacao tipoMovimentacao = pesquisarTipoMovimentacao(movimentacaoPatch.getTipoMovimentacao());
        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoPatch.getNumeroConteiner());

        Movimentacao movimentacaoLocalizada = buscarMovimentacaoPorTipoMovimentacaoConteiner(conteiner, tipoMovimentacao);
        movimentacaoLocalizada.setDataHoraFim(LocalDateTime.now());
        return MovimentacaoMapper.INSTANCE.toMovimentacaoPatchResponse(movimentacaoRepository.save(movimentacaoLocalizada));
    }

    private Movimentacao pesquisarPorIdOuLancarExcecao(Long id) {
       return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Movimentação não foi localizada"));
    }

    private Movimentacao buscarMovimentacaoPorTipoMovimentacaoConteiner(Conteiner conteiner, TipoMovimentacao tipoMovimentacao)  {
        return movimentacaoRepository.findByTipoMovimentacaoAndConteinerAndStatus(tipoMovimentacao, conteiner, true)
                .orElseThrow(()-> new BadRequestException("Movimentação não foi localizada"));

    }
}
