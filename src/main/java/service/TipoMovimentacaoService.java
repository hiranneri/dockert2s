package service;

import model.TipoMovimentacao;
import model.TipoMovimentacaoEnum;

import java.util.List;

public interface TipoMovimentacaoService {

    void criar(TipoMovimentacao movimentacao);

    void criar(List<TipoMovimentacao> tiposMovimentacoes);

    void editar(TipoMovimentacao movimentacao);

    void deletar(TipoMovimentacao movimentacao);

    TipoMovimentacao buscar(Long id);

    TipoMovimentacao buscar(TipoMovimentacaoEnum tipoMovimentacaoEnum);
}
