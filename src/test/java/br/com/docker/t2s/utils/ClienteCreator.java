package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.http.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.enums.Status;

import java.util.HashSet;

public class ClienteCreator {

    public static Cliente createClienteAtivo(){
        return Cliente.builder()
                .id(1L)
                .nome("TSC")
                .conteiner(new HashSet<>())
                .status(Status.ATIVO).build();
    }

    public static Cliente createClienteInativo(){
        return Cliente.builder()
                .nome("TSC")
                .status(Status.INATIVO).build();
    }

    public static ClientePostRequestDTO createClienteJaExistente(){
        return ClientePostRequestDTO.builder()
                .id(1L)
                .nome("TSC")
                .status(Status.ATIVO).build();
    }

    public static ClientePostRequestDTO createClientePostRequestValido() {
        return ClientePostRequestDTO.builder()
                .nome("TSC").build();
    }

    public static ClientePutRequestDTO createClienteRequestSemNome(){
        return ClientePutRequestDTO.builder()
                .id(1L).build();
    }

    public static ClientePutRequestDTO createClienteRequestValido(){
        return ClientePutRequestDTO.builder()
                .nome("TSC")
                .build();

    }

    public static ClientePostRequestDTO createClienteRequestComNomeInexistente(){
        return ClientePostRequestDTO.builder()
                .nome("Inexistente")
                .build();

    }

    public static ClientePutRequestDTO createClientePutSemID() {
        return ClientePutRequestDTO.builder()
                .nome("TSC Editado")
                .build();
    }



    public static ClienteResponseDTO createClienteResponseAtivo(){
        return ClienteResponseDTO.builder()
                .nome("TSC")
                .status(Status.ATIVO)
                .build();
    }

    public static ClienteResponseDTO createClienteResponseInativo(){
        return ClienteResponseDTO.builder()
                .nome("TSC")
                .status(Status.INATIVO)
                .build();
    }


    public static ClienteResponseDTO createClienteResponseEditadoAtivo() {
        return ClienteResponseDTO.builder()
                .nome("TSC Editado")
                .status(Status.ATIVO).build();
    }

    public static Cliente createClienteEditadoAtivo() {
        return Cliente.builder()
                .nome("TSC Editado")
                .status(Status.ATIVO).build();
    }



    public static ClientePutRequestDTO createClienteRequestEditado() {
        return ClientePutRequestDTO.builder()
                .id(1L)
                .nome("TSC Editado").build();
    }
}
