package br.com.docker.t2s.exceptions.requesthandler;

import br.com.docker.t2s.exceptions.DetalhesErroException;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DetalhesValidacaoException extends DetalhesErroException {

    private final String campos;
    private final String mensagemErro;

}
