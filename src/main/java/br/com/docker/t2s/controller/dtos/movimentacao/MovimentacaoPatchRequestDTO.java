package br.com.docker.t2s.controller.dtos.movimentacao;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class MovimentacaoPatchRequestDTO {

    private Long id; // opcional
    private String dataHoraFim; // opcional

    @NotNull(message = "O campo tipoMovimentacao é obrigatório")
    private String tipoMovimentacao;

    @NotNull(message = "O campo numeroConteiner é obrigatório")
    private String numeroConteiner;
}
