package br.com.bancoalpha.DesafioPraticoGoTech2024.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof ContaNaoEncontradaException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof PercentualParticipacaoInvalidoException) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(e.getMessage(), status);
    }

    public static class ContaNaoEncontradaException extends RuntimeException {
        public ContaNaoEncontradaException(String mensagem) {
            super(mensagem);
        }
    }

    public static class PercentualParticipacaoInvalidoException extends RuntimeException {
        public PercentualParticipacaoInvalidoException(String mensagem) {
            super(mensagem);
        }
    }
}