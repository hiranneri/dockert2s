package service;

import model.Cliente;

public interface ClienteService {

    void criar(Cliente cliente);

    void editar(Cliente cliente);

    void deletar(Cliente cliente);

    Cliente buscar(String nome);
}
