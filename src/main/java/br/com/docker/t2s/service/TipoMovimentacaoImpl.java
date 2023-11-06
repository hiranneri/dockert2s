package br.com.docker.t2s.service;

import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import br.com.docker.t2s.repository.TiposMovimentacaoRepository;
import br.com.docker.t2s.service.interfaces.TipoMovimentacaoService;
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
    public void criar(TipoMovimentacao movimentacao) {
        Transacao.realizarTransacaoCadastro(movimentacao);
    }

    @Override
    public void criar(List<TipoMovimentacao> tipoMovimentacaoMovimentacoes) {
        for(TipoMovimentacao tipo: tipoMovimentacaoMovimentacoes) {
            criar(tipo);
        }
    }

    @Override
    public void editar(TipoMovimentacao movimentacao) {

    }

    @Override
    public void deletar(TipoMovimentacao movimentacao) {

    }

    @Override
    public TipoMovimentacao buscar(Long id) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        return Transacao.ENTITY_MANAGER.find(TipoMovimentacao.class, id);
    }

    @Override
    public TipoMovimentacao buscar(NomeMovimentacao nomeMovimentacao) {
        EntityManager em = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT t FROM tipomovimentacoes t WHERE nome = :nome";

        Query query = em.createQuery(queryString);
        query.setParameter("nome", nomeMovimentacao);

        em.getTransaction().begin();
        List<TipoMovimentacao> tipos = query.getResultList();
        em.getTransaction().commit();
        System.out.println("na pesquisa" + tipos);

        return tipos.get(0);

    }

    /**
     * Método que realiza a parametrização do sistema cadastrando os tipos de movimentação
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void cadastrarTipoMovimentacao() {
        log.info("Iniciando a verificação para cadastro dos tipos de movimentação");

        List<TipoMovimentacao> tiposMovimentacoes = gerarListaTiposMovimentacoes();

        if(tiposMovimentacaoRepository.count() == 0){
            log.info("Iniciando cadastro no banco...");
            tiposMovimentacaoRepository.saveAll(tiposMovimentacoes);
        } else {
            log.info("Foi verificado que a tabela já está populada");
        }

        categoriaService.cadastrarCategorias();
    }

    List<TipoMovimentacao> gerarListaTiposMovimentacoes() {
        List<TipoMovimentacao> tiposMovimentacoes = new ArrayList<>();

        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.EMBARQUE).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.DESCARGA).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.GATE_IN).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.GATE_OUT).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.POSICIONAMENTO).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.PILHA).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.PESAGEM).build());
        tiposMovimentacoes.add(TipoMovimentacao.builder().nome(NomeMovimentacao.SCANNER).build());

        return tiposMovimentacoes;

    }
}
