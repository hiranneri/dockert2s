package br.com.docker.t2s.controller.dtos.cliente;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Data
public class ClientePostRequestDTO {

    @Null(message = "Campo id não deve ser informado")
    Long id;

    @NotNull(message = "Campo nome é obrigatório")
    String nome;

    @Null(message = "Campo status não deve ser informado")
    boolean status;
}
