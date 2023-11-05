package br.com.docker.t2s.controller.dtos.conteiner;

import br.com.docker.t2s.model.enums.Status;
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
    String status;

}
