package com.example.inventory.control.api;

/**
 * Стандартное тело ответа клиенту.
 */
public class BaseResponseBody {

    /**
     * Статус.
     */
    private StatusResponse status;

    /**
     * Описание.
     */
    private String description;

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
