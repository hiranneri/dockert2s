package br.com.docker.t2s.service.abstracao;

import br.com.docker.t2s.controller.http.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.http.MovimentacaoRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoService {

    MovimentacaoResponseDTO criar(MovimentacaoRequestDTO movimentacao);
    MovimentacaoResponseDTO buscarPorDataCriacao(LocalDateTime dataHoraCriacao);

    List<MovimentacaoResponseDTO> buscarMovimentacoes();

    Page<MovimentacaoResponseDTO> buscarMovimentacoes(Pageable pageable);

    MovimentacaoResponseDTO editarMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO);
    MovimentacaoResponseDTO deletarMovimentacao(Long id);

}
