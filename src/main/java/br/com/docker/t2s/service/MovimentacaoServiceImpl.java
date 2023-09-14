package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.http.MovimentacaoRequestDTO;
import br.com.docker.t2s.controller.http.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.http.mappers.movimentacao.MovimentacaoMapper;
import br.com.docker.t2s.exceptions.BadRequestException;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TiposMovimentacao;
import br.com.docker.t2s.model.enums.MovimentacaoEnum;
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
    public MovimentacaoResponseDTO criar(MovimentacaoRequestDTO movimentacaoRequestDTO) {
        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoRequestDTO.getNumeroConteiner());
        TiposMovimentacao tipoMovimentacao = pesquisarTipoMovimentacao(movimentacaoRequestDTO.getTipoMovimentacao());
        Movimentacao movimentacao = Movimentacao.builder()
                .conteiner(conteiner)
                .tiposMovimentacao(tipoMovimentacao)
                .dataHoraInicio(DataUtils.toLocalDateTime(movimentacaoRequestDTO.getDataHoraInicio()))
                .build();

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);

        MovimentacaoResponseDTO movimentacaoResponseDTO = MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoSalva);
        movimentacaoResponseDTO.setTipoMovimentacao(movimentacaoRequestDTO.getTipoMovimentacao());

        return movimentacaoResponseDTO;
    }

    private TiposMovimentacao pesquisarTipoMovimentacao(String nomeTipo) {
        MovimentacaoEnum movimentacaoEnum = MovimentacaoEnum.valueOf(nomeTipo);
        return tiposMovimentacaoRepository.findByNome(movimentacaoEnum)
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
    public MovimentacaoResponseDTO editarMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO) {
        pesquisarPorIdOuLancarExcecao(movimentacaoRequestDTO.getId());
        MovimentacaoEnum movimentacaoEnum = MovimentacaoEnum.valueOf(movimentacaoRequestDTO.getTipoMovimentacao());

        Conteiner conteiner = conteinerService.buscarConteinerCompletoPeloNumero(movimentacaoRequestDTO.getNumeroConteiner());
        TiposMovimentacao tiposMovimentacao = tiposMovimentacaoRepository.findByNome(movimentacaoEnum)
                .orElseThrow(()-> new BadRequestException("Tipo de Movimentação não localizada"));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(movimentacaoRequestDTO.getId());
        movimentacao.setDataHoraInicio(DataUtils.toLocalDateTime(movimentacaoRequestDTO.getDataHoraInicio()));
        movimentacao.setConteiner(conteiner);
        movimentacao.setTiposMovimentacao(tiposMovimentacao);
        movimentacao.setStatus(Status.ATIVO);

        return MovimentacaoMapper.INSTANCE.toMovimentacaoResponse(movimentacaoRepository.save(movimentacao));
    }

    private Movimentacao toMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO){
        return null;
    }

    @Override
    public MovimentacaoResponseDTO deletarMovimentacao(Long id) {
        Movimentacao movimentacaoLocalizada = pesquisarPorIdOuLancarExcecao(id);
        movimentacaoLocalizada.setStatus(Status.INATIVO);
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
}
