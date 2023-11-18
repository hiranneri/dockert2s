package br.com.docker.t2s.controller.dtos.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePostRequestDTO {

    @Null(message = "Campo id não deve ser informado")
    Long id;

    @NotNull(message = "Campo nome é obrigatório")
    String nome;
}
