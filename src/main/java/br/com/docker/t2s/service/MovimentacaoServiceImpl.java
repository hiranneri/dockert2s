package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.http.mappers.movimentacao.MovimentacaoMapper;
import br.com.docker.t2s.exceptions.BadRequestException;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TiposMovimentacao;
import br.com.docker.t2s.model.enums.NomeTipoMovimentacao;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.repository.MovimentacaoRepository;
import br.com.docker.t2s.repository.QueryTyper;
import br.com.docker.t2s.repository.TiposMovimentacaoRepository;
import br.com.docker.t2s.service.abstracao.ConteinerService;
import br.com.docker.t2s.service.abstracao.MovimentacaoService;
import br.com.docker.t2s.utils.DataUtils;
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
    public MovimentacaoResponseDTO criar(MovimentacaoPostRequestDTO movimentacaoPostRequestDTO) {
        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoPostRequestDTO.getNumeroConteiner());
        TiposMovimentacao tipoMovimentacao = pesquisarTipoMovimentacao(movimentacaoPostRequestDTO.getTipoMovimentacao());
        Movimentacao movimentacao = Movimentacao.builder()
                .conteiner(conteiner)
                .tiposMovimentacao(tipoMovimentacao)
                .build();

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);

        MovimentacaoResponseDTO movimentacaoResponseDTO = MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoSalva);
        movimentacaoResponseDTO.setTipoMovimentacao(movimentacaoPostRequestDTO.getTipoMovimentacao());

        return movimentacaoResponseDTO;
    }

    private TiposMovimentacao pesquisarTipoMovimentacao(String nomeTipo) {
        NomeTipoMovimentacao nomeTipoMovimentacao = NomeTipoMovimentacao.valueOf(nomeTipo);
        return tiposMovimentacaoRepository.findByNome(nomeTipoMovimentacao)
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
        pesquisarPorIdOuLancarExcecao(movimentacaoPostRequestDTO.getId());
        NomeTipoMovimentacao nomeTipoMovimentacao = NomeTipoMovimentacao.valueOf(movimentacaoPostRequestDTO.getTipoMovimentacao());

        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoPostRequestDTO.getNumeroConteiner());
        TiposMovimentacao tiposMovimentacao = tiposMovimentacaoRepository.findByNome(nomeTipoMovimentacao)
                .orElseThrow(()-> new BadRequestException("Tipo de Movimentação não localizada"));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(movimentacaoPostRequestDTO.getId());
        movimentacao.setDataHoraInicio(DataUtils.toLocalDateTime(movimentacaoPostRequestDTO.getDataHoraInicio()));
        movimentacao.setConteiner(conteiner);
        movimentacao.setTiposMovimentacao(tiposMovimentacao);
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
    public MovimentacaoResponseDTO finalizar(MovimentacaoPostRequestDTO movimentacaoPostRequestDTO) {
        String numeroConteiner = movimentacaoPostRequestDTO.getNumeroConteiner();
        String tipoMovimentacao = movimentacaoPostRequestDTO.getTipoMovimentacao();
        Movimentacao movimentacaoLocalizada = buscarPorNumeroConteinerTipoMovimentacao(numeroConteiner, tipoMovimentacao);
        movimentacaoLocalizada.setDataHoraFim(LocalDateTime.now());
        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoRepository.save(movimentacaoLocalizada));
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
    private Movimentacao buscarPorNumeroConteinerTipoMovimentacao(String numeroConteiner, String tipoMovimentacao)  {

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
