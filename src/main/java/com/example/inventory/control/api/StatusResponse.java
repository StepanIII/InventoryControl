package com.example.inventory.control.api;

public enum StatusResponse {
    SUCCESS,
    RESOURCE_NOT_FOUND,
    BENEFACTOR_NOT_FOUND,
    WAREHOUSE_NOT_FOUND,
    ACCEPT_NOT_FOUND,
    WRITE_OFF_NOT_FOUND,
    WAREHOUSE_RESOURCES_NOT_FOUND,
    NOT_ENOUGH_RESOURCES,
    MALFORMED_JSON_REQUEST,
    METHOD_ARGUMENT_NOT_VALID,
    INTERNAL_SERVER_ERROR,
    NOT_DELETE_PARENT
}
