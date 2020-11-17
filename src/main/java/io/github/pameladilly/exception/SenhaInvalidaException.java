package io.github.pameladilly.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
