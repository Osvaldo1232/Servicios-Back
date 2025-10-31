package com.primaria.app.exception;

public class BusinessException extends RuntimeException {
    private final int codigo;

    public BusinessException(int codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
