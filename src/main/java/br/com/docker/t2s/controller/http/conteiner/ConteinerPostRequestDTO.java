package br.com.docker.t2s.controller.http.conteiner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConteinerPostRequestDTO {
    Long id;
    String numero;
    String cliente;
    String tipo;
    String statusConteiner;
    String categoria;
}
