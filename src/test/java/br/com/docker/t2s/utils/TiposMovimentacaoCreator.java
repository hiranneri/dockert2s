package br.com.docker.t2s.utils;

import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;

import java.util.Optional;

public class TiposMovimentacaoCreator {
    public static Optional<TipoMovimentacao> createTiposMovimentacao() {
        return Optional.of(
                TipoMovimentacao.builder()
                        .id(1L)
                        .nome(NomeMovimentacao.EMBARQUE).build());
    }
}
