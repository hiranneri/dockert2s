package service.impl;

import model.Conteiner;
import repository.QueryTyper;
import service.ConteinerService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ConteinerServiceImpl implements ConteinerService {
    @Override
    public void criar(Conteiner conteiner) {
        Transacao.realizarTransacaoCadastro(conteiner);
    }

    @Override
    public void editar(Conteiner conteiner) {
        Transacao.ENTITY_MANAGER.flush();
    }

    @Override
    public void deletar(Conteiner conteiner) {
        Transacao.ENTITY_MANAGER.remove(conteiner);
        Transacao.ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public Conteiner buscar(String numero) {
        EntityManager entityManager = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT c FROM conteineres c WHERE c.numero = :numero";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("numero", numero);
        query.setFirstResult(0);

        entityManager.getTransaction().begin();
        Conteiner conteiner = QueryTyper.getPossibleSingleResult(query);
        entityManager.getTransaction().commit();

        return conteiner;
    }

    public List<Conteiner> buscarTodos() {
        EntityManager entityManager = Transacao.ENTITY_MANAGER;
        String queryString = "SELECT c FROM conteineres c ORDER BY c.id";
        Query query = entityManager.createQuery(queryString);

        entityManager.getTransaction().begin();
        List <Conteiner> conteiner = query.getResultList();
        entityManager.getTransaction().commit();

        return conteiner;
    }

    @Override
    public Conteiner buscar(Long id) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        Conteiner conteiner = Transacao.ENTITY_MANAGER.find(Conteiner.class, id);
        Transacao.ENTITY_MANAGER.getTransaction().commit();
        return conteiner;

    }
}
