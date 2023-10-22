package br.com.docker.t2s.controller.dtos.conteiner;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class ConteinerPutRequestDTO {

    @NotEmpty(message = "O campo id é obrigatório")
    private Long id;

    @NotEmpty(message = "O campo numero é obrigatório")
    private String numero;

    @NotEmpty(message = "O campo cliente é obrigatório")
    private String cliente;

    @NotEmpty(message = "O campo categoria é obrigatório")
    private String categoria;
}
