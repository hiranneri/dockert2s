package br.com.docker.t2s.controller.dtos.movimentacao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovimentacaoResponseDTO {

    String cliente;
    String numeroConteiner;
    String tipoMovimentacao;
    String dataHoraInicio;
}
