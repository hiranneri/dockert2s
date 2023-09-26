package br.com.docker.t2s.utils;

import br.com.docker.t2s.model.TiposMovimentacao;
import br.com.docker.t2s.model.enums.NomeTipoMovimentacao;

import java.util.Optional;

public class TiposMovimentacaoCreator {
    public static Optional<TiposMovimentacao> createTiposMovimentacao() {
        return Optional.of(
                TiposMovimentacao.builder()
                        .id(1L)
                        .nome(NomeTipoMovimentacao.EMBARQUE).build());
    }
}
