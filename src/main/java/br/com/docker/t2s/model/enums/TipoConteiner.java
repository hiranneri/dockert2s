package br.com.docker.t2s.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TipoConteiner {
    @JsonProperty("tipo")
    TIPO_20("20"),
    @JsonProperty("tipo")
    TIPO_40("40");

    private static Map<String, TipoConteiner> TIPOS_MAPA = Stream.of(TipoConteiner.values())
            .collect(Collectors.toMap(tipo -> tipo.valor, Function.identity()));

    private final String valor;

    TipoConteiner(String tipoConteiner) {
        this.valor = tipoConteiner;
    }

    @JsonCreator
    public static TipoConteiner fromRequest(String tipo) {
        return Optional
                .ofNullable(TIPOS_MAPA.get(tipo))
                .orElseThrow(() -> new IllegalArgumentException(tipo));
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
