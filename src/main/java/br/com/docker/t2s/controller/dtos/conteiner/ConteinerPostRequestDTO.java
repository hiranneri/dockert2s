package br.com.docker.t2s.controller.dtos.conteiner;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
public class ConteinerPostRequestDTO {

    @Null(message = "O campo id não deve ser informado")
    Long id;
    @NotNull(message = "O campo numero deve ser informado")
    String numero;
    @NotNull(message = "O campo nomeCliente deve ser informado")
    String nomeCliente;
    @NotNull(message = "O campo tipo deve ser informado")
    String tipo;
    @NotNull(message = "O campo status deve ser informado")
    String status;
    @NotNull(message = "O campo categoria deve ser informado")
    String categoria;
}
