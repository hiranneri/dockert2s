package br.com.docker.t2s.controller.dtos.cliente;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class ClientePutRequestDTO {

    Long id; // opcional
    @NotEmpty(message = "O campo nome é obrigatório")
    String nome;

}
