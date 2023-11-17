package br.com.docker.t2s.configurer;

import br.com.docker.t2s.exceptions.responsehandler.UnauthorizedException;
import br.com.docker.t2s.model.UsuarioDockerT2S;
import br.com.docker.t2s.repository.UserRepository;
import br.com.docker.t2s.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança para validação do token enviado em cada requisição
 */
@Component
@Log4j2
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String token = recoverToken(request);
            if(token != null){
                String username = tokenUtils.obterUsernameNoTokenValido(token);
                UsuarioDockerT2S usuario = userRepository.findByUsernameAndStatus(username,true)
                        .orElseThrow(()-> new UnauthorizedException("Token inválido"));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            log.info("Erro "+ e.getMessage());
            resolver.resolveException(request, response, null, e);
        }


    }

    public String recoverToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(authorization == null){
            return null;
        }
        return authorization.replace("Bearer ", "");
    }
}
