package br.com.docker.t2s.controller.dtos.login;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class LoginPostRequest {
    @NotEmpty(message = "Campo Username é obrigatório")
    String username;
    @NotEmpty(message = "Campo Senha é obrigatório")
    String senha;
}
