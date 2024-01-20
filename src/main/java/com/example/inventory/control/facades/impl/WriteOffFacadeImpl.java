package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.WriteOffFacade;
import com.example.inventory.control.services.WriteOffService;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffFacadeImpl implements WriteOffFacade {

    private final WriteOffService writeOffService;

    public WriteOffFacadeImpl(WriteOffService writeOffService) {
        this.writeOffService = writeOffService;
    }

    @Override
    public WriteOffsResponse getListAllWriteOff() {
        List<WriteOffResponse> writeOffResponseList = writeOffService
                .getListAllWriteOff().stream()
                .map(w -> new WriteOffResponse(w.id().orElseThrow(), w.getCreatedTime(), w.getWarehouse().getName()))
                .toList();
        return new WriteOffsResponse(writeOffResponseList);
    }

}
