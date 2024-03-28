package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientRequest;
import com.example.inventory.control.api.client.benefactor.ClientResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientsResponseBody;
import com.example.inventory.control.api.resources.ResourceResponseBody;

/**
 * Фасад для работы с клиентами.
 */
public interface ClientFacade {

    /**
     * Получить всех благодетелей.
     */
    BenefactorsResponseBody getAllBenefactors();

    /**
     * Получить всех благополучателей.
     *
     * @return тело ответа со статусом и благополучателями.
     */
    BeneficiariesResponseBody getAllBeneficiaries();

    /**
     * Получить всех клиентов.
     *
     * @return тело ответа со статусом и клиентами.
     */
    ClientsResponseBody getAllClients();

    /**
     * Добавить клиента.
     *
     * @param request запрос с данными клиента.
     *
     * @return тело ответа со статусом
     */
    BaseResponseBody addClient(ClientRequest request);

    /**
     * Обновить клиента.
     *
     * @param id      идентификатор клиента.
     * @param request запрос с данными.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody updateClient(Long id, ClientRequest request);

    /**
     * Получить клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     *
     * @return ответ со статусом и клиентом.
     */
    ClientResponseBody getClientById(Long id);

    /**
     * Удалить клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody deleteClient(Long id);
}
