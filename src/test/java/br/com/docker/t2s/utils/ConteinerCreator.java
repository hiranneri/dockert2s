package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.model.Categoria;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoCategoria;
import br.com.docker.t2s.model.enums.TipoConteiner;

import java.util.ArrayList;

public class ConteinerCreator {
    public static ConteinerResponseDTO createConteinerResponseAtivo() {
        return ConteinerResponseDTO.builder()
                .nomeCliente("Emirates")
                .numeroConteiner("TEST123457")
                .tipo("20")
                .status(Status.ATIVO.toString())
                .categoria("EXPORTACAO").build();
    }

    public static ConteinerResponseDTO createConteinerResponseEditadoAtivo() {
        return ConteinerResponseDTO.builder()
                .nomeCliente("TSC")
                .numeroConteiner("TEST999999")
                .tipo("20")
                .categoria("EXPORTACAO")
                .status("ATIVO").build();
    }

    public static ConteinerResponseDTO createConteinerResponseInativo() {
        return ConteinerResponseDTO.builder()
                .nomeCliente("TSC")
                .numeroConteiner("TEST123456")
                .tipo("20")
                .categoria("EXPORTACAO")
                .status("INATIVO").build();
    }

    public static ConteinerPostRequestDTO createConteinerPostRequestValido() {
        return ConteinerPostRequestDTO.builder()
                .nomeCliente("Emirates")
                .numero("TEST123457")
                .tipo("20")
                .categoria("EXPORTAÇÃO")
                .status("CHEIO")
                .build();
    }

    public static ConteinerPutRequestDTO createConteinerPutRequestValido() {
        return ConteinerPutRequestDTO.builder()
                .id(1L)
                .tipo("40")
                .status("CHEIO")
                .nomeCliente("Emirates")
                .numero("TEST999999")
                .categoria("IMPORTAÇÃO").build();
    }

    public static Conteiner createConteinerAtivo() {
        return Conteiner.builder()
                .id(1L)
                .cliente(new Cliente(1L,"TSC", new ArrayList<>(), Status.ATIVO))
                .numero("TEST123456")
                .tipo(TipoConteiner.TIPO_20)
                .statusConteiner(StatusConteiner.CHEIO)
                .categoria(new Categoria(1L, TipoCategoria.IMPORTACAO))
                .status(Status.ATIVO).build();
    }

    public static Conteiner createConteinerEditadoAtivo() {
        return Conteiner.builder()
                .id(1L)
                .cliente(new Cliente(1L,"TSC Editado", new ArrayList<>(), Status.ATIVO))
                .numero("TEST9999")
                .tipo(TipoConteiner.TIPO_20)
                .categoria(new Categoria(1L, TipoCategoria.IMPORTACAO))
                .statusConteiner(StatusConteiner.CHEIO)
                .status(Status.ATIVO).build();
    }

    public static Conteiner createConteinerInativo() {
        return Conteiner.builder()
                .id(3L)
                .cliente(new Cliente(2L,"TSC Inativo", new ArrayList<>(), Status.ATIVO))
                .numero("TEST123456")
                .categoria(new Categoria(1L, TipoCategoria.EXPORTACAO))
                .tipo(TipoConteiner.TIPO_20)
                .statusConteiner(StatusConteiner.CHEIO)
                .status(Status.INATIVO).build();
    }

    public static ConteinerPutRequestDTO createConteinerRequestEditado() {
        return ConteinerPutRequestDTO.builder()
                .id(1L)
                .tipo("20")
                .nomeCliente("TSC")
                .numero("TEST123456")
                .categoria("EXPORTAÇÃO")
                .status("CHEIO")
                .build();
    }

    public static ConteinerPostRequestDTO createConteinerJaExistente() {
        return ConteinerPostRequestDTO.builder()
                .numero("TEST123456")
                .nomeCliente("TSC")
                .tipo("20")
                .categoria("EXPORTAÇÃO")
                .status("CHEIO")
                .build();
    }

    public static ConteinerPutRequestDTO createConteinerRequestSemNumero() {
        return ConteinerPutRequestDTO.builder()
                .id(1L)
                .categoria("EXPORTACAO")
                .build();
    }

    public static ConteinerPutRequestDTO createConteinerPutSemID() {
        return ConteinerPutRequestDTO.builder()
                .numero("TEST123456")
                .categoria("EXPORTACAO")
                .build();
    }

    public static ConteinerPostRequestDTO createConteinerRequestComNomeInexistente() {
        return ConteinerPostRequestDTO.builder()
                .numero("TEST")
                .categoria("EXPORTACAO")
                .build();
    }
}
