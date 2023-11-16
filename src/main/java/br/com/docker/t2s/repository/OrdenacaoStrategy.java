package br.com.docker.t2s.repository;

import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdenacaoStrategy {

    private static final Map<String, OrdenacaoQueryStrategy> STRATEGY_MAP = new HashMap<>();

    public OrdenacaoStrategy(MovimentacaoRepository repository) {
        STRATEGY_MAP.put("cliente - ASC", new OrdenacaoPorClienteASC(repository));
        STRATEGY_MAP.put("cliente - DESC", new OrdenacaoPorClienteDESC(repository));
        STRATEGY_MAP.put("tipoMovimentacao - ASC", new OrdenacaoPorTipoMovimentacaoASC(repository));
        STRATEGY_MAP.put("tipoMovimentacao - DESC", new OrdenacaoPorTipoMovimentacaoDESC(repository));
    }

    public List<ResultadoRelatorioDTO> execute(String keyMapa){
        OrdenacaoQueryStrategy strategy = STRATEGY_MAP.get(keyMapa);
        return strategy.execute();
    }

}
