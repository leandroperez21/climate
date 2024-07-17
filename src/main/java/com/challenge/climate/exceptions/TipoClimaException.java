package com.challenge.climate.exceptions;

public class TipoClimaException extends RuntimeException {

    public TipoClimaException(String mensajeError) {
        super(mensajeError);
    }
}
