package br.com.docker.t2s.controller.dtos.mappers.register;

import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostResponse;
import br.com.docker.t2s.model.UsuarioDockerT2S;
import br.com.docker.t2s.utils.DataUtils;

public class UsuarioMapperImpl implements UsuarioMapper{

    @Override
    public UsuarioDockerT2S toUsuario(RegisterPostRequest registerPostRequest) {
        return UsuarioDockerT2S.builder()
                .name(registerPostRequest.getName())
                .username(registerPostRequest.getUsername())
                .password(registerPostRequest.getPassword())
                .authorities(registerPostRequest.getRoles()).build();
    }

    @Override
    public RegisterPostResponse toRegisterResponse(UsuarioDockerT2S usuarioDockerT2S) {

        return RegisterPostResponse.builder()
                .nome(usuarioDockerT2S.getName())
                .authorities(usuarioDockerT2S.getAuthorities())
                .dataHoraCriacao(DataUtils.dataHoraAtual()).build();
    }

}
