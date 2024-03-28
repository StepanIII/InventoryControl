package com.example.inventory.control.api.client.benefactor;

import com.example.inventory.control.api.BaseResponseBody;

import java.util.List;

/**
 * Ответ на запрос получения всех типов клиентов.
 */
public class ClientTypesResponseBody extends BaseResponseBody {

    /**
     * Типы клиентов.
     */
    private List<String> clientTypes;

    public List<String> getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(List<String> clientTypes) {
        this.clientTypes = clientTypes;
    }
}
