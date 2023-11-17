package br.com.docker.t2s.model.enums;

import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TipoCategoria {

    @JsonProperty("categoria")
    IMPORTACAO("IMPORTAÇÃO"),
    @JsonProperty("categoria")
    EXPORTACAO("EXPORTAÇÃO");

    private static final Map<String, TipoCategoria> TIPOS_MAPA = Stream.of(TipoCategoria.values())
            .collect(Collectors.toMap(categoria -> categoria.valor, Function.identity()));

    private final String valor;

    TipoCategoria(String status) {
        this.valor = status;
    }

    @JsonCreator
    public static TipoCategoria paraTipoCategoria(String tipoCategoriaInformado) throws BadRequestException{
        return Optional
                .ofNullable(TIPOS_MAPA.get(tipoCategoriaInformado))
                .orElseThrow(() -> new BadRequestException("Tipo de Categoria não localizado"));
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
