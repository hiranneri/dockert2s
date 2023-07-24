package service.impl;

import model.Movimentacao;
import repository.QueryTyper;
import service.MovimentacaoService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;

public class MovimentacaoServiceImpl implements MovimentacaoService {
    @Override
    public void criar(Movimentacao movimentacao) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        Transacao.ENTITY_MANAGER.persist(movimentacao);
        Transacao.ENTITY_MANAGER.getTransaction().commit();

    }

    /**
     * Informa o horário atual no data e hora final
     * @param movimentacao
     */
    @Override
    public void finalizarMovimentacao(Movimentacao movimentacao) {
        Transacao.realizarTransacaoCadastro(movimentacao);
    }

    @Override
    public void deletar(Movimentacao movimentacao) {
        Transacao.realizarTransacaoRemocao(movimentacao);

    }

    @Override
    public Movimentacao buscarPorDataCriacao(LocalDateTime dataCriacao) {
        return null;
    }


    @Override
    public Movimentacao buscarPorDataCriacao(String dataHoraCriacao)  {

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
