package repository;

import model.TipoMovimentacao;
import model.TipoMovimentacaoEnum;
import service.impl.Transacao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RelatorioRepository {

    public void deletar(TipoMovimentacao movimentacao) {

    }

    public TipoMovimentacao buscar(Long id) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        return Transacao.ENTITY_MANAGER.find(TipoMovimentacao.class, id);
    }

    public TipoMovimentacao buscar(TipoMovimentacaoEnum tipoMovimentacaoEnum) {
        EntityManager em = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT t FROM tipomovimentacoes t WHERE nome = :nome ORDER BY id asc";

        Query query = em.createQuery(queryString);
        query.setParameter("nome", tipoMovimentacaoEnum);

        em.getTransaction().begin();
        TipoMovimentacao tipoMovimentacao = QueryTyper.getPossibleSingleResult(query);
        em.getTransaction().commit();

        return tipoMovimentacao;

    }
}
