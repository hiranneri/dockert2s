package br.com.docker.t2s.controller.http.movimentacao;

import br.com.docker.t2s.model.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovimentacaoResponseDTO {

    String cliente;
    String numeroConteiner;
    String categoriaConteiner;
    String tipoMovimentacao;
    String dataHoraInicio;
    String dataHoraFim;
    Status status;
}
