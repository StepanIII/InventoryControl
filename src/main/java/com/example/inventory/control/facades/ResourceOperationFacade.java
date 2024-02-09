package com.example.inventory.control.facades;

import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.issue.AllIssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.IssueRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResponseBody;

/**
 * Фасад для работы с операциями над ресурсами.
 */
public interface ResourceOperationFacade {

    /**
     * Получить все приемки ресурсов.
     */
    AllAcceptResponseBody getAllAccept();

    /**
     * Получить приемку ресурсов по идентификатору.
     *
     * @param id идентификатор приемки.
     * @return ответ со статусом и найденной приемкой.
     */
    AcceptResponseBody getAcceptById(Long id);

    /**
     * Добавить приемку ресурсов.
     *
     * @param request запрос с данными приемки.
     * @return ответ со статусом.
     */
    BaseResponseBody addAccept(AcceptRequestBody request);

    /**
     * Получить все выдачи ресурсов.
     *
     * @return ответ со статусом и выдачами ресурсов.
     */
    AllIssueResponseBody getAllIssue();

    /**
     * Получить выдачу ресурсов по идентификатору.
     *
     * @param id идентификатор выдачи.
     *
     * @return ответ со статусом и выдачей ресурсов.
     */
    IssueResponseBody getIssueById(Long id);

    /**
     * Добавить выдачу ресурсов.
     *
     * @param request запрос с данными выдачи.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addIssue(IssueRequestBody request);
}
