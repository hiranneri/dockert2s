package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.model.*;
import br.com.docker.t2s.model.enums.*;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoCreator {
    public static MovimentacaoPostRequestDTO createMovimentacaoRequestValido() {
        return MovimentacaoPostRequestDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123458")
                .tipoMovimentacao("DESCARGA")
                .build();
    }

    public static MovimentacaoPostRequestDTO createMovimentacaoRequestValido2() {
        return MovimentacaoPostRequestDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST000000")
                .tipoMovimentacao("DESCARGA")
                .build();
    }

    public static MovimentacaoPostRequestDTO createMovimentacaoRequestValido3() {
        return MovimentacaoPostRequestDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123458")
                .tipoMovimentacao("EMBARQUE")
                .build();
    }

    public static MovimentacaoResponseDTO createMovimentacaoResponseAtivo() {
        return MovimentacaoResponseDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123456789")
                .tipoMovimentacao("DESCARGA")
                .dataHoraInicio("23/09/2023 09:00").build();
    }

    public static MovimentacaoResponseDTO createMovimentacaoResponseEditadoAtivo() {
        return MovimentacaoResponseDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123456789")
                .tipoMovimentacao("GATE_IN")
                .dataHoraInicio("23/09/2023 10:00")
                .build();
    }
    public static MovimentacaoPutRequestDTO createMovimentacaoRequestEditado() {
        return MovimentacaoPutRequestDTO.builder()
                .id(1L)
                .cliente("TSC")
                .numeroConteiner("TEST123456789")
                .categoria("EXPORTACAO")
                .tipoMovimentacao("GATE_IN")
                .build();
    }



    public static MovimentacaoPutRequestDTO createMovimentacaoPutRequestValido() {
        return MovimentacaoPutRequestDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST000000")
                .categoria("IMPORTAÇÃO")
                .tipoMovimentacao("GATE_IN")
                .build();
    }

    public static MovimentacaoPutRequestDTO createMovimentacaoASerFinalizadaValida() {
        return MovimentacaoPutRequestDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123458")
                .categoria("IMPORTAÇÃO")
                .tipoMovimentacao("GATE_IN")
                .build();
    }

    public static MovimentacaoResponseDTO createMovimentacaoResponseInativo() {
        return MovimentacaoResponseDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123456789")
                .tipoMovimentacao("EMBARQUE")
                .dataHoraInicio("23/09/2023 10:00")
                .build();
    }

    public static Movimentacao createMovimentacao() {
        return Movimentacao.builder()
                .id(1L)
                .dataHoraInicio(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .tipoMovimentacao(new TipoMovimentacao(1L, NomeMovimentacao.EMBARQUE, new ArrayList<>(), true))
                .conteiner(
                        new Conteiner(
                                1L,"", TipoConteiner.TIPO_20, StatusConteiner.CHEIO,
                                TipoCategoria.EXPORTACAO, new Cliente(),
                                new ArrayList<>(List.of(Movimentacao.builder().build())),true))
                .build();

    }

    public static Movimentacao createMovimentacaoEditada() {
        return Movimentacao.builder()
                .id(1L)
                .dataHoraInicio(LocalDateTime.now().plusDays(1))
                .updatedAt(LocalDateTime.now())
                .tipoMovimentacao(
                        new TipoMovimentacao(
                                1L,
                                NomeMovimentacao.EMBARQUE,
                                new ArrayList<>(List.of(MovimentacaoCreator.createMovimentacao())),
                                true
                        )
                )
                .conteiner(
                        new Conteiner(
                                1L,"", TipoConteiner.TIPO_20, StatusConteiner.CHEIO,
                                TipoCategoria.EXPORTACAO, new Cliente(),
                                new ArrayList<>(List.of(Movimentacao.builder().build())),true))
                .status(true)
                .build();
    }

    public static MovimentacaoPostRequestDTO createMovimentacaoJaExistente() {
        return MovimentacaoPostRequestDTO.builder()
                .cliente("TSC")
                .tipoMovimentacao("DESCARGA")
                .build();
    }

    public static MovimentacaoPutRequestDTO createMovimentacaoRequestSemTipo() {
        return MovimentacaoPutRequestDTO.builder()
                .id(1L)
                .cliente("TSC")
                .build();
    }

    public static MovimentacaoPutRequestDTO createMovimentacaoSemID() {
        return MovimentacaoPutRequestDTO.builder()
                .cliente("TSC")
                .build();
    }


}
