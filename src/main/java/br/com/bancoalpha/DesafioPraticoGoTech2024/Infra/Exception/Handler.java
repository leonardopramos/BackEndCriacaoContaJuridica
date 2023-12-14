package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<String> handleContaNaoEncontradaException(ContaNaoEncontradaException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return new ResponseEntity<>("Ocorreu um erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ContaNaoEncontradaException extends RuntimeException {
        public ContaNaoEncontradaException(String mensagem) {
            super(mensagem);
        }
    }
}
