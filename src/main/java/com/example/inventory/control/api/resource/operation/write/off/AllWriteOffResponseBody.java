package com.example.inventory.control.api.resource.operation.write.off;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationResponseBodyModel;
import com.example.inventory.control.api.resource.operation.write.off.model.WriteOffResponseBodyModel;

import java.util.List;

/**
 * Тело ответа на запрос получения всех списаний ресурсов.
 */
public class AllWriteOffResponseBody extends BaseResponseBody {

    /**
     * Списания.
     */
    private List<WriteOffResponseBodyModel> writeOffs;

    public List<WriteOffResponseBodyModel> getWriteOffs() {
        return writeOffs;
    }

    public void setWriteOffs(List<WriteOffResponseBodyModel> writeOffs) {
        this.writeOffs = writeOffs;
    }
}
