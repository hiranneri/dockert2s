package br.com.docker.t2s.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum StatusConteiner {
    @JsonProperty("statusConteiner")
    CHEIO("CHEIO"),
    @JsonProperty("statusConteiner")
    VAZIO("VAZIO");
    private static Map<String, StatusConteiner> TIPOS_MAPA = Stream.of(StatusConteiner.values())
            .collect(Collectors.toMap(status -> status.valor, Function.identity()));

    private final String valor;

    StatusConteiner(String status) {
        this.valor = status;
    }

    @JsonCreator
    public static StatusConteiner fromRequest(String status) {
        return Optional
                .ofNullable(TIPOS_MAPA.get(status))
                .orElseThrow(() -> new IllegalArgumentException(status));
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
