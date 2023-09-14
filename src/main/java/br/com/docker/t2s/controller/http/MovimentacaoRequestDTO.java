package br.com.docker.t2s.controller.http;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MovimentacaoRequestDTO {

    Long id;
    String cliente;
    String numeroConteiner;
    String tipoMovimentacao;
    String dataHoraInicio;
    String dataHoraFim;

}
