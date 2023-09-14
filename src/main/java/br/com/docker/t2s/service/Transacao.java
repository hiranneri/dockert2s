package br.com.docker.t2s.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Transacao {
    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("dockert2s");
    public final static EntityManager ENTITY_MANAGER = EMF.createEntityManager();


    public static void realizarTransacaoCadastro(Object object) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(object);
        ENTITY_MANAGER.getTransaction().commit();
    }

    public static void realizarTransacaoRemocao(Object object) {
        Transacao.ENTITY_MANAGER.getTransaction().begin();
        Transacao.ENTITY_MANAGER.remove(object);
        Transacao.ENTITY_MANAGER.getTransaction().commit();
    }
}
