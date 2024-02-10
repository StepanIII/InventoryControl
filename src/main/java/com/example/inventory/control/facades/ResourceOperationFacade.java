package com.example.inventory.control.facades;

import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.AllCapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationRequestBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.issue.AllIssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.IssueRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.AllWriteOffResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.WriteOffRequestBody;
import com.example.inventory.control.api.resource.operation.write.off.WriteOffResponseBody;

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

    /**
     * Получить все оприходования.
     *
     * @return ответ со статусом и оприходованиями.
     */
    AllCapitalizationResponseBody getAllCapitalization();

    /**
     * Получить оприходование по идентификатору.
     *
     * @param id идентификатор оприходования.
     *
     * @return ответ со статусом и оприходованием.
     */
    CapitalizationResponseBody getCapitalizationById(Long id);

    /**
     * Добавить оприходование ресурсов.
     *
     * @param request запрос с данными оприходования.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addCapitalization(CapitalizationRequestBody request);

    /**
     * Получить все списания.
     *
     * @return ответ со статусом и списаниями.
     */
    AllWriteOffResponseBody getAllWriteOff();

    /**
     * Получить списание по идентификатору.
     *
     * @param id идентификатор списания.
     *
     * @return ответ со статусом и списанием.
     */
    WriteOffResponseBody getWriteOffById(Long id);

    /**
     * Добавить списание.
     *
     * @param request запрос с данными списания.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addWriteOff(WriteOffRequestBody request);
}
