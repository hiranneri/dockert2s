package br.com.docker.t2s.controller.dtos.login;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginPostResponse {

    private String dataHora;
    private String mensagem;
    private String tempoExpiracaoTokenEmMinutos;
    private String token;

}
