package com.example.inventory.control.api.resource.operation.issue;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.issue.model.IssueWithResourcesBodyModel;

/**
 * Ответ на запрос получения выдачи.
 */
public class IssueResponseBody extends BaseResponseBody {

    /**
     * Выдача.
     */
    private IssueWithResourcesBodyModel issue;

    public IssueWithResourcesBodyModel getIssue() {
        return issue;
    }

    public void setIssue(IssueWithResourcesBodyModel issue) {
        this.issue = issue;
    }
}
