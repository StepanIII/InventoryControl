package com.example.inventory.control.ui.models.responses.writeoff;


import java.util.List;

/**
 * Тело ответа "Приемки".
 */
public class WriteOffsResponse {

    private List<WriteOffResponse> writeOffs;

    public WriteOffsResponse() {
    }

    public WriteOffsResponse(List<WriteOffResponse> writeOffs) {
        this.writeOffs = writeOffs;
    }

    public List<WriteOffResponse> getWriteOffs() {
        return writeOffs;
    }

    public void setWriteOffs(List<WriteOffResponse> writeOffs) {
        this.writeOffs = writeOffs;
    }
}
