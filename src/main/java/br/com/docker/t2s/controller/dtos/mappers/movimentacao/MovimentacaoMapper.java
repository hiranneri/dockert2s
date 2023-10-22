package br.com.docker.t2s.controller.dtos.mappers.movimentacao;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.model.Movimentacao;
import org.mapstruct.factory.Mappers;

public interface MovimentacaoMapper {

    MovimentacaoMapper INSTANCE = Mappers.getMapper(MovimentacaoMapper.class);


    MovimentacaoResponseDTO toMovimentacaoResponse(Movimentacao movimentacaoResponseDTO);
}
