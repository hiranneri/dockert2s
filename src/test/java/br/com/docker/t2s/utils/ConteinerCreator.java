package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.http.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.http.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.http.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoConteiner;

import java.util.HashSet;

public class ConteinerCreator {
    public static ConteinerResponseDTO createConteinerResponseAtivo() {
        return ConteinerResponseDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123456")
                .tipo("20")
                .status(Status.ATIVO)
                .categoria("EXPORTACAO").build();
    }

    public static ConteinerResponseDTO createConteinerResponseEditadoAtivo() {
        return ConteinerResponseDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123456")
                .tipo("20")
                .categoria("EXPORTACAO")
                .status(Status.ATIVO).build();
    }

    public static ConteinerResponseDTO createConteinerResponseInativo() {
        return ConteinerResponseDTO.builder()
                .cliente("TSC")
                .numeroConteiner("TEST123456")
                .tipo("20")
                .categoria("EXPORTACAO")
                .status(Status.INATIVO).build();
    }

    public static ConteinerPostRequestDTO createConteinerPostRequestValido() {
        return ConteinerPostRequestDTO.builder()
                .cliente("TSC")
                .numero("TEST123456")
                .tipo("20")
                .categoria("EXPORTACAO")
                .statusConteiner("CHEIO")
                .build();
    }

    public static ConteinerPutRequestDTO createConteinerPutRequestValido() {
        return ConteinerPutRequestDTO.builder()
                .id(1L)
                .cliente("TSC")
                .numero("TEST123456")
                .categoria("EXPORTACAO").build();
    }

    public static Conteiner createConteinerAtivo() {
        return Conteiner.builder()
                .id(1L)
                .cliente(new Cliente(1L,"TSC", new HashSet<>(), Status.ATIVO))
                .numero("TEST123456")
                .tipo(TipoConteiner.TIPO_20)
                .statusConteiner(StatusConteiner.CHEIO)
                .status(Status.ATIVO).build();
    }

    public static Conteiner createConteinerEditadoAtivo() {
        return Conteiner.builder()
                .id(2L)
                .cliente(new Cliente(1L,"TSC Editado", new HashSet<>(), Status.ATIVO))
                .numero("TEST9999")
                .tipo(TipoConteiner.TIPO_20)
                .statusConteiner(StatusConteiner.CHEIO)
                .status(Status.ATIVO).build();
    }

    public static Conteiner createConteinerInativo() {
        return Conteiner.builder()
                .id(3L)
                .cliente(new Cliente(2L,"TSC Inativo", new HashSet<>(), Status.ATIVO))
                .numero("TEST123456")
                .tipo(TipoConteiner.TIPO_20)
                .statusConteiner(StatusConteiner.CHEIO)
                .status(Status.INATIVO).build();
    }

    public static ConteinerPutRequestDTO createConteinerRequestEditado() {
        return ConteinerPutRequestDTO.builder()
                .id(1L)
                .cliente("TSC")
                .numero("TEST123456")
                .categoria("EXPORTACAO")
                .build();
    }

    public static ConteinerPostRequestDTO createConteinerJaExistente() {
        return ConteinerPostRequestDTO.builder()
                .numero("TEST123456")
                .cliente("TSC")
                .tipo("20")
                .categoria("EXPORTACAO")
                .statusConteiner("CHEIO")
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
