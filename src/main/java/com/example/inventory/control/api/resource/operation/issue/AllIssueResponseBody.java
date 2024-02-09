package com.example.inventory.control.api.resource.operation.issue;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.issue.model.IssueResponseBodyModel;

import java.util.List;

/**
 * Тело ответа на запрос получения всех выдач.
 */
public class AllIssueResponseBody extends BaseResponseBody {

    /**
     * Выдачи.
     */
    private List<IssueResponseBodyModel> issuance;

    public List<IssueResponseBodyModel> getIssuance() {
        return issuance;
    }

    public void setIssuance(List<IssueResponseBodyModel> issuance) {
        this.issuance = issuance;
    }
}
