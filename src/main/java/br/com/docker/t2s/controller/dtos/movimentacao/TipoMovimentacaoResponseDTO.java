package br.com.docker.t2s.controller.dtos.movimentacao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TipoMovimentacaoResponseDTO {

    private String nome;
}
