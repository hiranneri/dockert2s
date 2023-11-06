package br.com.docker.t2s.service.interfaces;

import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;

import java.util.List;

public interface TipoMovimentacaoService {

    void criar(TipoMovimentacao movimentacao);

    void criar(List<TipoMovimentacao> tipoMovimentacaoMovimentacoes);

    void editar(TipoMovimentacao movimentacao);

    void deletar(TipoMovimentacao movimentacao);

    TipoMovimentacao buscar(Long id);

    TipoMovimentacao buscar(NomeMovimentacao nomeMovimentacao);
}
