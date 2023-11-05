package br.com.docker.t2s.controller.dtos.registerUsuario;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class RegisterPostResponse {

    String nome;
    Collection<? extends GrantedAuthority> authorities;
    String dataHoraCriacao;
}
