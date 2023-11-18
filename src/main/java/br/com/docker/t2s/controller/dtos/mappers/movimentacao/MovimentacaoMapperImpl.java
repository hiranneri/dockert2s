package br.com.docker.t2s.controller.dtos.mappers.movimentacao;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPatchResponseDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoResponseDTO;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import br.com.docker.t2s.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class MovimentacaoMapperImpl implements MovimentacaoMapper{


    @Override
    public MovimentacaoResponseDTO toMovimentacaoResponse(Movimentacao movimentacao) {

        return MovimentacaoResponseDTO.builder()
                .cliente(movimentacao.getConteiner().getCliente().getNome())
                .numeroConteiner(movimentacao.getConteiner().getNumero())
                .tipoMovimentacao(movimentacao.getTipoMovimentacao().getNome().toString())
                .dataHoraInicio(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraInicio()))
                .status(converterStatus(movimentacao.isStatus()))
                .build();
    }

    @Override
    public MovimentacaoPatchResponseDTO toMovimentacaoPatchResponse(Movimentacao movimentacao) {
        return MovimentacaoPatchResponseDTO.builder()
                .numeroConteiner(movimentacao.getConteiner().getNumero())
                .tipoMovimentacao(movimentacao.getTipoMovimentacao().getNome().toString())
                .dataHoraFim(DataUtils.convertLocalDateTimeToString(movimentacao.getDataHoraFim()))
                .status(converterStatus(movimentacao.isStatus()))
                .build();
    }

    @Override
    public List<TipoMovimentacao> toTiposMovimentacao(List<TipoMovimentacaoPostRequestDTO> tiposRequest) {
        List<TipoMovimentacao> tiposMovimentacao = new ArrayList<>();
        for(TipoMovimentacaoPostRequestDTO tipoRequest: tiposRequest) {
            tiposMovimentacao.add(
                    TipoMovimentacao.builder()
                            .nome(NomeMovimentacao.valueOf(tipoRequest.getNome())).build()
            );
        }
        return tiposMovimentacao;
    }

    @Override
    public List<TipoMovimentacaoResponseDTO> toTiposMovimentacaoResponse(List<TipoMovimentacao> tipoMovimentacoes) {
        List<TipoMovimentacaoResponseDTO> tiposMovimentacao = new ArrayList<>();
        for(TipoMovimentacao tipoMovimentacao: tipoMovimentacoes) {
            tiposMovimentacao.add(
                    TipoMovimentacaoResponseDTO.builder()
                            .nome(tipoMovimentacao.getNome().toString()).build()
            );
        }
        return tiposMovimentacao;
    }

    String converterStatus(boolean status){
        if(status){
            return "Ativo";
        }else {
            return "Inativo";
        }
    }
}
