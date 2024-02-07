package com.example.inventory.control.api;

/**
 * Модель ответа для клиента в случае возникновения ошибки на сервере.
 */
public final class ExceptionResponse {

    /**
     * Код ошибки.
     */
    private final StatusResponse errorCode;

    /**
     * Описание.
     */
    private final String errorDescription;

    public ExceptionResponse(StatusResponse errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public StatusResponse getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
