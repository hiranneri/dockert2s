package br.com.docker.t2s.utils;

import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;

public class UsuarioCreator {

    public static RegisterPostRequest createUsuario(){
        return RegisterPostRequest.builder()
                .name("novousuario")
                .username("novo")
                .password("novo")
                .roles("ROLE_USER").build();
    }
}
