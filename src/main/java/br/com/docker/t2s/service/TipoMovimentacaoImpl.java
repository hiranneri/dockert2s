package br.com.docker.t2s.service;

import br.com.docker.t2s.model.TiposMovimentacao;
import br.com.docker.t2s.model.enums.MovimentacaoEnum;
import br.com.docker.t2s.repository.TiposMovimentacaoRepository;
import br.com.docker.t2s.service.abstracao.TipoMovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class TipoMovimentacaoImpl implements TipoMovimentacaoService {

    private final TiposMovimentacaoRepository tiposMovimentacaoRepository;
    private final CategoriaService categoriaService;

    @Override
    public void criar(TiposMovimentacao movimentacao) {
        Transacao.realizarTransacaoCadastro(movimentacao);
    }

    @Override
    public void criar(List<TiposMovimentacao> tiposMovimentacaoMovimentacoes) {
        for(TiposMovimentacao tipo: tiposMovimentacaoMovimentacoes) {
            criar(tipo);
        }
    }

    @Override
    public void editar(TiposMovimentacao movimentacao) {

    }

    @Override
    public void deletar(TiposMovimentacao movimentacao) {

    }

    @Override
    public TiposMovimentacao buscar(Long id) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        return Transacao.ENTITY_MANAGER.find(TiposMovimentacao.class, id);
    }

    @Override
    public TiposMovimentacao buscar(MovimentacaoEnum movimentacaoEnum) {
        EntityManager em = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT t FROM tipomovimentacoes t WHERE nome = :nome";

        Query query = em.createQuery(queryString);
        query.setParameter("nome", movimentacaoEnum);

        em.getTransaction().begin();
        List<TiposMovimentacao> tipos = query.getResultList();
        em.getTransaction().commit();
        System.out.println("na pesquisa" + tipos);

        return tipos.get(0);

    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void cadastrarTipoMovimentacao() {
        log.info("Iniciando a verificação para cadastro dos tipos de movimentação");

        List<TiposMovimentacao> tiposMovimentacoes = gerarListaTiposMovimentacoes();

        if(tiposMovimentacaoRepository.count() == 0){
            log.info("Iniciando cadastro no banco...");
            tiposMovimentacaoRepository.saveAll(tiposMovimentacoes);
        } else {
            log.info("Foi verificado que a tabela já está populada");
        }

        categoriaService.cadastrarCategorias();
    }

    List<TiposMovimentacao> gerarListaTiposMovimentacoes() {
        List<TiposMovimentacao> tiposMovimentacoes = new ArrayList<>();

        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.EMBARQUE).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.DESCARGA).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.GATE_IN).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.GATE_OUT).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.POSICIONAMENTO).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.PILHA).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.PESAGEM).build());
        tiposMovimentacoes.add(TiposMovimentacao.builder().nome(MovimentacaoEnum.SCANNER).build());

        return tiposMovimentacoes;

    }
}
