package br.com.docker.t2s.controller.dtos.conteiner;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class ConteinerPutRequestDTO {

    private Long id; // opcional

    @NotEmpty(message = "O campo numero é obrigatório")
    private String numero;

    @NotEmpty(message = "O campo tipo é obrigatório")
    private String tipo;

    @NotEmpty(message = "O campo status é obrigatório")
    private String status;

    @NotEmpty(message = "O campo categoria é obrigatório")
    private String categoria;

    @NotEmpty(message = "O campo nomeCliente é obrigatório")
    private String nomeCliente;

}
