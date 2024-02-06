package com.example.inventory.control.api.writeoff;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.writeoff.model.WriteOffResourcesBody;

/**
 * Ответ на запрос получения спианий.
 */
public class WriteOffResourcesResponse extends BaseResponse {

    /**
     * Списание.
     */
    private WriteOffResourcesBody writeOffResources;

    public WriteOffResourcesBody getWriteOffResources() {
        return writeOffResources;
    }

    public void setWriteOffResources(WriteOffResourcesBody writeOffResources) {
        this.writeOffResources = writeOffResources;
    }
}
