package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoListWrapper;
import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoPostRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class TipoMovimentacaoCreator {

    public static TipoMovimentacaoListWrapper createTiposMovimentacao(){
        List<TipoMovimentacaoPostRequestDTO> tipos = new ArrayList<>();

        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("DESCARGA").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("EMBARQUE").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("GATE_IN").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("GATE_OUT").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("PESAGEM").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("PILHA").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("POSICIONAMENTO").build());
        tipos.add(TipoMovimentacaoPostRequestDTO.builder().nome("SCANNER").build());

        return TipoMovimentacaoListWrapper.builder().tipos(tipos).build();
    }
}
