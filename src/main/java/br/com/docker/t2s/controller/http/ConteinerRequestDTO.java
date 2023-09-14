package br.com.docker.t2s.controller.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConteinerRequestDTO {
    Long id;
    String numero;
    String cliente;
    String tipo;
    String statusConteiner;
    String categoria;
}
