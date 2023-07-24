package service.impl;

import model.Cliente;
import service.ClienteService;

public class ClienteServiceImpl implements ClienteService {

    @Override
    public void criar(Cliente cliente) {
        Transacao.realizarTransacaoCadastro(cliente);
    }

    @Override
    public void editar(Cliente cliente) {

    }

    @Override
    public void deletar(Cliente cliente) {

    }

    @Override
    public Cliente buscar(String nome) {
        return null;
    }
}
