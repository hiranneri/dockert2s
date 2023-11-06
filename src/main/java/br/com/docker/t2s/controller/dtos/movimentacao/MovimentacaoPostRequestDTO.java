package br.com.docker.t2s.controller.dtos.movimentacao;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class MovimentacaoPostRequestDTO {

    @NotEmpty(message = "O campo cliente é obrigatório")
    String cliente;

    @NotEmpty(message = "O campo numeroConteiner é obrigatório")
    String numeroConteiner;

    @NotEmpty(message = "O campo tipoMovimentacao é obrigatório")
    String tipoMovimentacao;
}
