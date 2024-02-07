package com.example.inventory.control.api;

/**
 * Модель ответа для клиента в случае возникновения ошибки на сервере.
 */
public final class ExceptionResponse {

    /**
     * Код ошибки.
     */
    private StatusResponse code;

    /**
     * Описание.
     */
    private String description;

    public ExceptionResponse(StatusResponse code, String description) {
        this.code = code;
        this.description = description;
    }

    public StatusResponse getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
