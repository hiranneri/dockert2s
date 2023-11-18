package br.com.docker.t2s.repository.strategies;

import br.com.docker.t2s.repository.MovimentacaoRepository;
import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;

import java.util.List;

public class OrdenacaoPorTipoMovimentacaoASC implements OrdenacaoQueryStrategy {

    private final MovimentacaoRepository movimentacaoRepository;

    public OrdenacaoPorTipoMovimentacaoASC(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public List<ResultadoRelatorioDTO> execute() {
       return movimentacaoRepository.obterTotalAgrupadoPorClienteETipoOrdenadoPorTipoMovimentacaoASC();
    }
}
