package com.example.inventory.control.api.resource.operation.write.off;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.model.WriteOffWithCaseResponseBodyModel;

/**
 * Тело ответа на запрос получение списания.
 */
public class WriteOffResponseBody extends BaseResponseBody {

    /**
     * Списание.
     */
    private WriteOffWithCaseResponseBodyModel writeOff;

    public WriteOffWithCaseResponseBodyModel getWriteOff() {
        return writeOff;
    }

    public void setWriteOff(WriteOffWithCaseResponseBodyModel writeOff) {
        this.writeOff = writeOff;
    }
}
