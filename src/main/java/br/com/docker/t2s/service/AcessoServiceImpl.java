package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.dtos.login.LoginPostRequest;
import br.com.docker.t2s.controller.dtos.login.LoginPostResponse;
import br.com.docker.t2s.controller.dtos.mappers.register.UsuarioMapper;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostResponse;
import br.com.docker.t2s.model.UsuarioDockerT2S;
import br.com.docker.t2s.repository.UserRepository;
import br.com.docker.t2s.service.dto.TokenDTO;
import br.com.docker.t2s.service.interfaces.AcessoService;
import br.com.docker.t2s.utils.DataUtils;
import br.com.docker.t2s.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AcessoServiceImpl implements AcessoService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    @Override
    public LoginPostResponse login(LoginPostRequest login) {
        UsernamePasswordAuthenticationToken loginESenha =
                new UsernamePasswordAuthenticationToken(login.getUsername(),
                        login.getSenha());

        Authentication auth = this.authenticationManager.authenticate(loginESenha);
        log.info("Usuário autenticado!");

        UsuarioDockerT2S usuario = (UsuarioDockerT2S) auth.getPrincipal();
        TokenDTO jwt = tokenUtils.gerarToken(usuario);

        return LoginPostResponse.builder()
                .dataHora(LocalDateTime.now().format(DataUtils.PT_BR_FORMATTER))
                .mensagem("Usuário logado com sucesso. Por favor, se atente ao tempo de expiração do token")
                .tempoExpiracaoTokenEmMinutos(jwt.getTempoExpiracaoEmMinutos().toString())
                .token(jwt.getToken()).build();
    }

    @Override
    public RegisterPostResponse cadastrar(RegisterPostRequest registro) {
        registro.setPassword(new BCryptPasswordEncoder().encode(registro.getPassword()));
        UsuarioDockerT2S usuarioDockerT2S = UsuarioMapper.INSTANCE.toUsuario(registro);
        UsuarioDockerT2S usuarioSalvo = userRepository.save(usuarioDockerT2S);
        return UsuarioMapper.INSTANCE.toRegisterResponse(usuarioSalvo);

    }
    public void cadastrar(List<RegisterPostRequest> usuarios) {

        for(RegisterPostRequest usuario: usuarios){
            usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            UsuarioDockerT2S usuarioDockerT2S = UsuarioMapper.INSTANCE.toUsuario(usuario);
            userRepository.save(usuarioDockerT2S);
        }

    }
}
