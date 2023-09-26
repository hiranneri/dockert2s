package br.com.docker.t2s.controller.http.movimentacao;

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

    @NotEmpty(message = "O campo categoria é obrigatório")
    String categoria;
}
