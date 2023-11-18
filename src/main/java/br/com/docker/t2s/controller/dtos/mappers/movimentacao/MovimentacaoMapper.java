package br.com.docker.t2s.controller.dtos.mappers.movimentacao;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPatchResponseDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoResponseDTO;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface MovimentacaoMapper {

    MovimentacaoMapper INSTANCE = Mappers.getMapper(MovimentacaoMapper.class);
    MovimentacaoResponseDTO toMovimentacaoResponse(Movimentacao movimentacaoResponseDTO);
    MovimentacaoPatchResponseDTO toMovimentacaoPatchResponse(Movimentacao movimentacaoResponseDTO);
    List<TipoMovimentacao> toTiposMovimentacao(List<TipoMovimentacaoPostRequestDTO> tiposRequest);
    List<TipoMovimentacaoResponseDTO> toTiposMovimentacaoResponse(List<TipoMovimentacao> tiposRequest);
}
