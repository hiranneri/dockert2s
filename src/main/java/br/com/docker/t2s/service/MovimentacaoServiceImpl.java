package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.dtos.mappers.movimentacao.MovimentacaoMapper;
import br.com.docker.t2s.controller.dtos.movimentacao.*;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import br.com.docker.t2s.repository.MovimentacaoRepository;
import br.com.docker.t2s.repository.QueryTyper;
import br.com.docker.t2s.repository.TiposMovimentacaoRepository;
import br.com.docker.t2s.service.interfaces.ConteinerService;
import br.com.docker.t2s.service.interfaces.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        if (movimentacaoRepository.existsByTipoMovimentacaoAndConteiner(tipoMovimentacao,conteiner)) {
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
        return tiposMovimentacaoRepository.findByNome(nomeMovimentacao)
                .orElseThrow((
                        ()-> new BadRequestException("Tipo de Movimentação não foi localizada"))
                );
    }

    @Override
    public MovimentacaoResponseDTO buscarPorDataCriacao(LocalDateTime dataHoraCriacao)  {
        // Será implementado em relatórios
        return null;
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
        TipoMovimentacao tipoMovimentacao = tiposMovimentacaoRepository.findByNome(nomeMovimentacao)
                .orElseThrow(()-> new BadRequestException("Tipo de Movimentação não localizada"));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(movimentacaoPostRequestDTO.getId());
        movimentacao.setDataHoraInicio(dataHoraInicio);
        movimentacao.setConteiner(conteiner);
        movimentacao.setTipoMovimentacao(tipoMovimentacao);
        movimentacao.setStatus(Status.ATIVO);

        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoRepository.save(movimentacao));
    }
    @Override
    public MovimentacaoResponseDTO deletarMovimentacao(Long id) {
        Movimentacao movimentacaoLocalizada = pesquisarPorIdOuLancarExcecao(id);
        movimentacaoLocalizada.setStatus(Status.INATIVO);
        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoRepository.save(movimentacaoLocalizada));
    }

    @Override
    public MovimentacaoPatchResponseDTO finalizar(MovimentacaoPatchRequestDTO movimentacao) {
        String numeroConteiner = movimentacao.getNumeroConteiner();
        String tipoMovimentacao = movimentacao.getTipoMovimentacao();
        Movimentacao movimentacaoLocalizada = buscarMovimentacaoPorNumeroConteinerTipoMovimentacao(numeroConteiner, tipoMovimentacao);
        movimentacaoLocalizada.setDataHoraFim(LocalDateTime.now());
        return MovimentacaoMapper.INSTANCE.toMovimentacaoPatchResponse(movimentacaoRepository.save(movimentacaoLocalizada));
    }

    private Movimentacao pesquisarPorIdOuLancarExcecao(Long id) {
       return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Movimentação não foi localizada"));
    }

    private Movimentacao buscarPorDataCriacao(String dataHoraCriacao)  {

        EntityManager entityManager = Transacao.ENTITY_MANAGER;
        String queryString = "FROM movimentacoes m WHERE m.datahorainicio = :dataHoraInicio";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("dataHoraInicio", dataHoraCriacao);

        entityManager.getTransaction().begin();
        Movimentacao movimentacao = QueryTyper.getPossibleSingleResult(query);
        entityManager.getTransaction().commit();

        return movimentacao;

    }

    // Analisar parametros informados para a busca
    private Movimentacao buscarMovimentacaoPorNumeroConteinerTipoMovimentacao(String numeroConteiner, String tipoMovimentacao)  {

        EntityManager entityManager = Transacao.ENTITY_MANAGER;
        String queryString = "FROM movimentacoes m WHERE m.datahorainicio = :dataHoraInicio";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("dataHoraInicio", numeroConteiner);

        entityManager.getTransaction().begin();
        Movimentacao movimentacao = QueryTyper.getPossibleSingleResult(query);
        entityManager.getTransaction().commit();

        return movimentacao;

    }
}
