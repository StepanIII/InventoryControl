package com.example.inventory.control.services;

import com.example.inventory.control.models.Acceptance;
import com.example.inventory.control.models.WriteOff;

import java.util.List;

public interface WriteOffService {

    /**
     * Получить список всех Списаний.
     */
    List<WriteOff> getListAllWriteOff();

}
