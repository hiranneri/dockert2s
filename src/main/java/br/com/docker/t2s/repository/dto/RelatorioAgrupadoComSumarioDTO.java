package br.com.docker.t2s.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Classe responsável por definir como o relatório será apresentado na response body
 */
@Data
@AllArgsConstructor
public class RelatorioAgrupadoComSumarioDTO {

    private final List<ResultadoRelatorioDTO> relatorio;
    private final List<SumarioDTO> sumario;

    public static RelatorioAgrupadoComSumarioDTO gerarRelatorio(List<ResultadoRelatorioDTO> resultado, List<SumarioDTO> sumarioDTO){
        return new RelatorioAgrupadoComSumarioDTO(resultado,sumarioDTO);
    }
}
