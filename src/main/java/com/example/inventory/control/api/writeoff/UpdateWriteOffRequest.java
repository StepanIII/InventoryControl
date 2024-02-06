package com.example.inventory.control.api.writeoff;

import com.example.inventory.control.api.writeoff.WriteOffResourceCountRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Модель запроса для обновления приемки.
 */
public class UpdateWriteOffRequest {

    @NotNull
    private Long warehouseId;

    @NotNull
    @Size(min = 1)
    private List<WriteOffResourceCountRequest> resources;


}
