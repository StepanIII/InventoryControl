package com.example.inventory.control.models;

/**
 * Результат выполенения бизнесс процесса.
 */
public final class ResultExecution <T> {

    /**
     * Статус выполенения бизнесс процесса.
     */
    private final Status status;

    /**
     * Описание результата выполенения бизнесс процесса.
     */
    private final String description;

    private final T result;

    public ResultExecution(Status status, String description) {
        this.status = status;
        this.description = description;
        this.result = null;
    }

    public ResultExecution(Status status, String description, T result) {
        this.status = status;
        this.description = description;
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public T getResult() {
        return result;
    }

    /**
     * Статус выполенения бизнесс процесса.
     */
    public enum Status {
        SUCCESS,
        ERROR
    }
}
