package service.impl;

import model.Cliente;
import repository.QueryTyper;
import service.ClienteService;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ClienteServiceImpl implements ClienteService {

    @Override
    public void criar(Cliente cliente) {
        Transacao.realizarTransacaoCadastro(cliente);
    }

    @Override
    public void editar(Cliente cliente) {
        Transacao.realizarTransacaoCadastro(cliente);

    }

    @Override
    public void deletar(Cliente cliente) {
        Transacao.realizarTransacaoRemocao(cliente);
    }

    @Override
    public Cliente buscar(String nome) {
        EntityManager entityManager = Transacao.ENTITY_MANAGER;
        String queryString = "FROM clientes c WHERE c.nome = :nome limit 1";
        Query query = entityManager.createQuery(queryString);

        entityManager.getTransaction().begin();
        Cliente clienteLocalizado = QueryTyper.getPossibleSingleResult(query);
        entityManager.getTransaction().commit();
        return clienteLocalizado;
    }
}
