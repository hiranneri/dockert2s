package service.impl;

import model.TipoMovimentacao;
import model.TipoMovimentacaoEnum;
import service.TipoMovimentacaoService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TipoMovimentacaoImpl implements TipoMovimentacaoService {


    @Override
    public void criar(TipoMovimentacao movimentacao) {
        Transacao.realizarTransacaoCadastro(movimentacao);
    }

    @Override
    public void criar(List<TipoMovimentacao> tiposMovimentacoes) {
        for(TipoMovimentacao tipo: tiposMovimentacoes) {
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
    public TipoMovimentacao buscar(TipoMovimentacaoEnum tipoMovimentacaoEnum) {
        EntityManager em = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT t FROM tipomovimentacoes t WHERE nome = :nome";

        Query query = em.createQuery(queryString);
        query.setParameter("nome", tipoMovimentacaoEnum);

        em.getTransaction().begin();
        List<TipoMovimentacao> tipos = query.getResultList();
        em.getTransaction().commit();
        System.out.println("na pesquisa" + tipos);

        return tipos.get(0);

    }
}
