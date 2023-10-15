package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.TiposMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import br.com.docker.t2s.service.Transacao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RelatorioRepository {

    public void deletar(TiposMovimentacao movimentacao) {

    }

    public TiposMovimentacao buscar(Long id) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        return Transacao.ENTITY_MANAGER.find(TiposMovimentacao.class, id);
    }

    public TiposMovimentacao buscar(NomeMovimentacao nomeMovimentacao) {
        EntityManager em = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT t FROM tipomovimentacoes t WHERE nome = :nome ORDER BY id asc";

        Query query = em.createQuery(queryString);
        query.setParameter("nome", nomeMovimentacao);

        em.getTransaction().begin();
        TiposMovimentacao tiposMovimentacao = QueryTyper.getPossibleSingleResult(query);
        em.getTransaction().commit();

        return tiposMovimentacao;

    }
}
