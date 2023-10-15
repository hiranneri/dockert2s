package br.com.docker.t2s.service.abstracao;

import br.com.docker.t2s.model.TiposMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;

import java.util.List;

public interface TipoMovimentacaoService {

    void criar(TiposMovimentacao movimentacao);

    void criar(List<TiposMovimentacao> tiposMovimentacaoMovimentacoes);

    void editar(TiposMovimentacao movimentacao);

    void deletar(TiposMovimentacao movimentacao);

    TiposMovimentacao buscar(Long id);

    TiposMovimentacao buscar(NomeMovimentacao nomeMovimentacao);
}
