package service;

import model.Conteiner;

public interface ConteinerService {

    void criar(Conteiner conteiner);

    void editar(Conteiner conteiner);

    void deletar(Conteiner conteiner);

    Conteiner buscar(String numero);
    Conteiner buscar(Long id);
}
