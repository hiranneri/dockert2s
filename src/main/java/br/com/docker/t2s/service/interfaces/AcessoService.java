package br.com.docker.t2s.service.interfaces;

import br.com.docker.t2s.controller.dtos.login.LoginPostRequest;
import br.com.docker.t2s.controller.dtos.login.LoginPostResponse;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostResponse;
import org.springframework.stereotype.Service;

@Service
public interface AcessoService {

    LoginPostResponse login(LoginPostRequest login);

    RegisterPostResponse cadastrar(RegisterPostRequest registro);
}
