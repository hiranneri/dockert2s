package br.com.docker.t2s.controller.dtos.registerUsuario;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class RegisterPostRequest {

    @NotBlank(message = "Campo Nome é obrigatório")
    private String name;

    @NotBlank(message = "Campo Username é obrigatório")
    private String username;

    @NotBlank(message = "Campo Password é obrigatório")
    private String password;

    @NotBlank(message = "Campo Roles é obrigatório")
    private String roles;

}
