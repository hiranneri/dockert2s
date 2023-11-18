package br.com.docker.t2s.service.interfaces;

import br.com.docker.t2s.controller.dtos.movimentacao.*;
import br.com.docker.t2s.repository.dto.RelatorioAgrupadoComSumarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovimentacaoService {

    MovimentacaoResponseDTO criar(MovimentacaoPostRequestDTO movimentacao);

    List<MovimentacaoResponseDTO> buscarMovimentacoes();

    Page<MovimentacaoResponseDTO> buscarMovimentacoes(Pageable pageable);

    MovimentacaoResponseDTO editarMovimentacao(MovimentacaoPutRequestDTO movimentacaoPostRequestDTO);
    MovimentacaoResponseDTO deletarMovimentacao(Long id);

    MovimentacaoPatchResponseDTO finalizar(MovimentacaoPatchRequestDTO movimentacaoPatchRequestDTO);

    RelatorioAgrupadoComSumarioDTO gerarRelatoriosMovimentacoesAgrupadas(String campo, String ordenacao);

    List<TipoMovimentacaoResponseDTO> criarTiposMovimentacao(TipoMovimentacaoListWrapper tipos);
}
