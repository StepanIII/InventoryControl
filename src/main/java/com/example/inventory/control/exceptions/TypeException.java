package com.example.inventory.control.exceptions;

/**
 * Выбрасывается если тип доменное модели не соответствует контексту.
 */
public final class TypeException extends RuntimeException {

    public TypeException(String message) {
        super(message);
    }
}
