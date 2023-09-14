package br.com.docker.t2s.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HttpMessageNotReadableException extends RuntimeException{

    public HttpMessageNotReadableException(String message) {
        super(message);
    }
}
