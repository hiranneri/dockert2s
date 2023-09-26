package br.com.docker.t2s.controller.http.mappers.movimentacao;

import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.utils.DataUtils;

public class MovimentacaoMapperImpl implements MovimentacaoMapper{


    @Override
    public MovimentacaoResponseDTO toMovimentacaoResponse(Movimentacao movimentacao) {

        // Adicionar o tipo de movimentação

        MovimentacaoResponseDTO movimentacaoResponseDTO = MovimentacaoResponseDTO.builder()
                .cliente(movimentacao.getConteiner().getCliente().getNome())
                .numeroConteiner(movimentacao.getConteiner().getNumero())
                .dataHoraInicio(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraInicio()))
                .dataHoraFim(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraFim()))
                .status(movimentacao.getStatus())
                .build();


        return movimentacaoResponseDTO;
    }
}
