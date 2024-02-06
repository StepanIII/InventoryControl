package com.example.inventory.control.api.writeoff;


import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.api.writeoff.model.WriteOffBody;

import java.util.List;

/**
 * Ответ на запрос получения спианий.
 */
public class WriteOffsResponse extends BaseResponse {

    /**
     * Списания.
     */
    private List<WriteOffBody> writeOffs;

    public List<WriteOffBody> getWriteOffs() {
        return writeOffs;
    }

    public void setWriteOffs(List<WriteOffBody> writeOffs) {
        this.writeOffs = writeOffs;
    }
}
