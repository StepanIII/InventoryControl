package com.example.inventory.control.api.client.benefactor;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.client.benefactor.model.ClientModel;
import com.example.inventory.control.api.resources.model.ResourceDto;

/**
 * Ответ на запрос клиента.
 */
public class ClientResponseBody extends BaseResponseBody {

    /**
     * Клиент.
     */
    private ClientModel client;

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }
}
