package br.com.docker.t2s.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDTO {
    private String token;
    private Long tempoExpiracaoEmMinutos;

}
