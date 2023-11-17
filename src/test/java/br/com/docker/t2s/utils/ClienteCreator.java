package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;

import java.util.ArrayList;

public class ClienteCreator {

    public static Cliente createClienteAtivo(){
        return Cliente.builder()
                .id(1L)
                .nome("TSC")
                .conteiner(new ArrayList<>())
                .status(true).build();
    }

    public static Cliente createClienteInativo(){
        return Cliente.builder()
                .nome("TSC")
                .status(false).build();
    }

    public static ClientePostRequestDTO createClienteJaExistente(){
        return ClientePostRequestDTO.builder()
                .id(1L)
                .nome("TSC")
                .status(true).build();
    }

    public static ClientePostRequestDTO createClientePostRequestValido() {
        return ClientePostRequestDTO.builder()
                .nome("TSC Movimentacao 1").build();
    }

    public static ClientePostRequestDTO createClienteRequestValido2() {
        return ClientePostRequestDTO.builder()
                .nome("TESTES").build();
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
                .status(true)
                .build();
    }

    public static ClienteResponseDTO createClienteResponseInativo(){
        return ClienteResponseDTO.builder()
                .nome("TSC")
                .status(false)
                .build();
    }


    public static ClienteResponseDTO createClienteResponseEditadoAtivo() {
        return ClienteResponseDTO.builder()
                .nome("TSC Editado")
                .status(true).build();
    }

    public static Cliente createClienteEditadoAtivo() {
        return Cliente.builder()
                .nome("TSC Editado")
                .status(true).build();
    }



    public static ClientePutRequestDTO createClienteRequestEditado() {
        return ClientePutRequestDTO.builder()
                .id(1L)
                .nome("TSC Editado").build();
    }
}
