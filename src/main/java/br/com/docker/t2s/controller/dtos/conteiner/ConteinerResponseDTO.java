package br.com.docker.t2s.controller.dtos.conteiner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConteinerResponseDTO {

    String numeroConteiner;
    String tipo;
    String statusConteiner;
    String categoria;
    String nomeCliente;
    boolean status;

}
