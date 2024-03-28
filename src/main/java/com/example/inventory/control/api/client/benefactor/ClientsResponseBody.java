package com.example.inventory.control.api.client.benefactor;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.ClientResponseBodyModel;

import java.util.List;

/**
 * Ответ на запрос получения всех клиентов.
 */
public class ClientsResponseBody extends BaseResponseBody {

    /**
     * Клиенты.
     */
    private List<ClientResponseBodyModel> clients;

    public List<ClientResponseBodyModel> getClients() {
        return clients;
    }

    public void setClients(List<ClientResponseBodyModel> clients) {
        this.clients = clients;
    }
}
