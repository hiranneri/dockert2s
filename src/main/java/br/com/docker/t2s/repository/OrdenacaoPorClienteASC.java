package br.com.docker.t2s.repository;

import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;

import java.util.List;

public class OrdenacaoPorClienteASC implements OrdenacaoQueryStrategy {

    public MovimentacaoRepository movimentacaoRepository;

    public OrdenacaoPorClienteASC(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public List<ResultadoRelatorioDTO> execute() {
        return movimentacaoRepository.obterTotalAgrupadoPorClienteETipoOrdenadoPorClienteASC();

    }
}
