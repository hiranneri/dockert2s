package br.com.docker.t2s.controller.dtos.movimentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoMovimentacaoPostRequestDTO {

    private String nome;
}
