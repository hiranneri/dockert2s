package br.com.docker.t2s.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class DetalhesErroException {
    protected final String titulo;
    protected final LocalDateTime dataHora;
    protected final int status;
    protected final String mensagem;


}
