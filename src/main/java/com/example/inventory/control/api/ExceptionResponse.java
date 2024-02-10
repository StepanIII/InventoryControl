package com.example.inventory.control.api;

/**
 * Модель ответа для клиента в случае возникновения ошибки на сервере.
 */
public final class ExceptionResponse {

    /**
     * Код ошибки.
     */
    private final StatusResponse status;

    /**
     * Описание.
     */
    private final String description;

    public ExceptionResponse(StatusResponse status, String description) {
        this.status = status;
        this.description = description;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
