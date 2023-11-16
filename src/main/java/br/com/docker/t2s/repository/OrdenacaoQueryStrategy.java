package br.com.docker.t2s.repository;

import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;

import java.util.List;

public interface OrdenacaoQueryStrategy {

    List<ResultadoRelatorioDTO> execute();
}
