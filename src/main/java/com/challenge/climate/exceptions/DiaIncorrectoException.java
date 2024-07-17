package com.challenge.climate.exceptions;

public class DiaIncorrectoException extends RuntimeException {

    public DiaIncorrectoException(String mensajeError) {
        super(mensajeError);
    }
}
