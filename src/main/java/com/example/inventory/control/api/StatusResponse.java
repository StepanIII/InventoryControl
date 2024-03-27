package com.example.inventory.control.api;

public enum StatusResponse {
    SUCCESS,
    RESOURCE_NOT_FOUND,
    BENEFACTOR_NOT_FOUND,
    BENEFICIARY_NOT_FOUND,
    WAREHOUSE_NOT_FOUND,
    ACCEPT_NOT_FOUND,
    ISSUE_NOT_FOUND,
    CAPITALIZATION_NOT_FOUND,
    WRITE_OFF_NOT_FOUND,
    INVENTORY_NOT_FOUND,
    MOVE_NOT_FOUND,
    ROLE_NOT_FOUND,
    WAREHOUSE_RESOURCES_NOT_FOUND,
    NOT_ENOUGH_RESOURCES,
    MALFORMED_JSON_REQUEST,
    METHOD_ARGUMENT_NOT_VALID,
    INTERNAL_SERVER_ERROR,
    NOT_DELETE_PARENT,
    NOT_VALID_LOGIN,
    NOT_VALID_EMAIL,
    USER_NOT_FOUND
}
