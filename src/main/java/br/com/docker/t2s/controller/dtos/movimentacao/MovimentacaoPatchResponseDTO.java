package br.com.docker.t2s.controller.dtos.movimentacao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MovimentacaoPatchResponseDTO {

    private String dataHoraFim;
    private String tipoMovimentacao;
    private String numeroConteiner;
}
