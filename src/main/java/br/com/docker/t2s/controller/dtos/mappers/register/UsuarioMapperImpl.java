package br.com.docker.t2s.controller.dtos.mappers.register;

import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
import br.com.docker.t2s.model.UsuarioDockerT2S;

public class UsuarioMapperImpl implements UsuarioMapper{

    @Override
    public UsuarioDockerT2S toUsuario(RegisterPostRequest registerPostRequest) {
        return UsuarioDockerT2S.builder()
                .name(registerPostRequest.getName())
                .username(registerPostRequest.getUsername())
                .password(registerPostRequest.getPassword())
                .authorities(registerPostRequest.getRoles()).build();
    }

}
