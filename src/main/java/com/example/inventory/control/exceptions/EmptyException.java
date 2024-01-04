package com.example.inventory.control.exceptions;

/**
 * Выбрасывается, когда приложение пытается использовать пустую строку.
 */
public final class EmptyException extends RuntimeException {

    public EmptyException(String message) {
        super(message);
    }
}
