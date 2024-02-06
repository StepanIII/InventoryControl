package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.WriteOff;

import java.util.List;
import java.util.Optional;

public interface WriteOffService {

    /**
     * Получить список всех списаний.
     */
    List<WriteOff> getListAllWriteOff();

    /**
     * Получить списание по идентификатору.
     *
     * @param id идентификатор искомого списания.
     * @return найденное списание.
     */
    Optional<WriteOff> find(Long id);

    /**
     * Сохранить списание.
     *
     * @param writeOff сохраняемое списание.
     * @return сохраненное списание.
     */
    WriteOff save(WriteOff writeOff);

    /**
     * Обновить списание
     * @param writeOff обновляемое списание.
     * @return обновленное списание.
     */
    WriteOff update(WriteOff writeOff);
}
