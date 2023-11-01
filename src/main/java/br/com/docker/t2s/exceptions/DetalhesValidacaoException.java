package br.com.docker.t2s.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DetalhesValidacaoException extends DetalhesErroException {

    private final String campos;
    private final String mensagemErro;

}
