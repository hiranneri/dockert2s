package br.com.docker.t2s.controller.http.movimentacao;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class MovimentacaoPutRequestDTO {

    @NotEmpty(message = "O campo id é obrigatório")
    Long id;

    @NotEmpty(message = "O campo cliente é obrigatório")
    String cliente;

    @NotEmpty(message = "O campo numeroConteiner é obrigatório")
    String numeroConteiner;

    @NotEmpty(message = "O campo tipoMovimentacao é obrigatório")
    String tipoMovimentacao;

    @NotEmpty(message = "O campo dataHoraInicio é obrigatório")
    String dataHoraInicio;

    @NotEmpty(message = "O campo categoria é obrigatório")
    String categoria;
}
