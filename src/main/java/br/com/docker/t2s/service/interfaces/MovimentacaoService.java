package br.com.docker.t2s.service.interfaces;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPostRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoService {

    MovimentacaoResponseDTO criar(MovimentacaoPostRequestDTO movimentacao);
    MovimentacaoResponseDTO buscarPorDataCriacao(LocalDateTime dataHoraCriacao);

    List<MovimentacaoResponseDTO> buscarMovimentacoes();

    Page<MovimentacaoResponseDTO> buscarMovimentacoes(Pageable pageable);

    MovimentacaoResponseDTO editarMovimentacao(MovimentacaoPutRequestDTO movimentacaoPostRequestDTO);
    MovimentacaoResponseDTO deletarMovimentacao(Long id);

    MovimentacaoResponseDTO finalizar(MovimentacaoPostRequestDTO movimentacaoPostRequestDTO);
}