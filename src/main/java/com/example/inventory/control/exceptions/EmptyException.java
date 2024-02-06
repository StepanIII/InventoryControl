package com.example.inventory.control.exceptions;

/**
 * Выбрасывается, когда в качестве параметра передается пустая строка.
 */
public final class EmptyException extends RuntimeException {

    public EmptyException(String message) {
        super(message);
    }
}
