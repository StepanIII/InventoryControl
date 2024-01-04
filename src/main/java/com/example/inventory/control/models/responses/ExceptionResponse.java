package com.example.inventory.control.models.responses;

/**
 * Модель ответа для клиента в случае возникновения ошибки на сервере.
 */
public final class ExceptionResponse {

    /**
     * Сообщение.
     */
    private String message;

    /**
     * Описание.
     */
    private String description;

    public ExceptionResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
