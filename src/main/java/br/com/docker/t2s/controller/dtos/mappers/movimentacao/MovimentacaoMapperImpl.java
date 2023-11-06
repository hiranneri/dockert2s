package br.com.docker.t2s.controller.dtos.mappers.movimentacao;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPatchResponseDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.utils.DataUtils;

public class MovimentacaoMapperImpl implements MovimentacaoMapper{


    @Override
    public MovimentacaoResponseDTO toMovimentacaoResponse(Movimentacao movimentacao) {

        return MovimentacaoResponseDTO.builder()
                .cliente(movimentacao.getConteiner().getCliente().getNome())
                .numeroConteiner(movimentacao.getConteiner().getNumero())
                .tipoMovimentacao(movimentacao.getTipoMovimentacao().getNome().toString())
                .dataHoraInicio(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraInicio()))
                .dataHoraFim(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraFim()))
                .status(movimentacao.getStatus())
                .build();
    }

    @Override
    public MovimentacaoPatchResponseDTO toMovimentacaoPatchResponse(Movimentacao movimentacao) {
        return MovimentacaoPatchResponseDTO.builder()
                .numeroConteiner(movimentacao.getConteiner().getNumero())
                .tipoMovimentacao(movimentacao.getTipoMovimentacao().getNome().toString())
                .dataHoraFim(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraFim()))
                .build();
    }
}
