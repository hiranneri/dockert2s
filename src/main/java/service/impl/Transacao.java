package service.impl;

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
}
