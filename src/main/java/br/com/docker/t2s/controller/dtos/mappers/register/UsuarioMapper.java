package br.com.docker.t2s.controller.dtos.mappers.register;

import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
import br.com.docker.t2s.model.UsuarioDockerT2S;
import org.mapstruct.factory.Mappers;

public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    UsuarioDockerT2S toUsuario(RegisterPostRequest registerPostRequest);
}
