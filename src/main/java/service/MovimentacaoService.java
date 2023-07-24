package service;

import model.Movimentacao;

import java.text.ParseException;
import java.time.LocalDateTime;

public interface MovimentacaoService {

    void criar(Movimentacao movimentacao);

    void finalizarMovimentacao(Movimentacao movimentacao);

    void deletar(Movimentacao movimentacao);

    Movimentacao buscarPorDataCriacao(LocalDateTime dataCriacao);

    Movimentacao buscarPorDataCriacao(String dataHoraCriacao);
}
