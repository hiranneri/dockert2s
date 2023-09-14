package br.com.docker.t2s.controller.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConteinerResponseDTO {

    String cliente;
    String numeroConteiner;
    String tipo;
    String statusConteiner;
    String categoria;


}
