package br.com.docker.t2s.repository;

import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;

import java.util.List;

public class OrdenacaoPorTipoMovimentacaoDESC implements OrdenacaoQueryStrategy{

    private MovimentacaoRepository movimentacaoRepository;

    public OrdenacaoPorTipoMovimentacaoDESC(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public List<ResultadoRelatorioDTO> execute() {
       return movimentacaoRepository.obterTotalAgrupadoPorClienteETipoOrdenadoPorTipoMovimentacaoDESC();
    }
}
