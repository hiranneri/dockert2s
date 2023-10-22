package br.com.docker.t2s.exceptions;

import br.com.docker.t2s.exceptions.requesthandler.DetalhesValidacaoException;
import br.com.docker.t2s.exceptions.responsehandler.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por tratar as exceções que ocorrem na camada Controller
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MENSAGEM_ERRO_ENVIO = "Erro no envio dos dados";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DetalhesErroException> handleBadRequestException(BadRequestException badRequest) {
        return new ResponseEntity<DetalhesErroException>(
                DetalhesErroException.builder()
                        .titulo("Bad Request Exception")
                        .dataHora(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .mensagem(badRequest.getMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<DetalhesErroException> handleUnauthorizedException(UnauthorizedException unauthorizedException) {
        return new ResponseEntity<DetalhesErroException>(
                DetalhesErroException.builder()
                        .titulo("Não autorizado")
                        .dataHora(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .mensagem(unauthorizedException.getMessage())
                        .build(), HttpStatus.UNAUTHORIZED
        );
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            MethodArgumentNotValidException ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> camposComErro = ex.getBindingResult().getFieldErrors();
        String campos = camposComErro.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String mensagensCampo = camposComErro.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        DetalhesErroException detalhesExcecao = DetalhesValidacaoException.builder()
                .titulo(MENSAGEM_ERRO_ENVIO)
                .dataHora(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .campos(campos)
                .mensagem(MENSAGEM_ERRO_ENVIO)
                .mensagemErro(mensagensCampo)
                .build();

        return new ResponseEntity<>(detalhesExcecao, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, null, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<Object>(
                DetalhesErroException.builder()
                        .titulo("Bad Request Exception")
                        .dataHora(LocalDateTime.now())
                        .status(status.value())
                        .mensagem("Não foi possível ler os dados enviados")
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

}
