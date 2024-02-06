package com.example.inventory.control.utils;

import java.util.List;

// Удалить
/**
 * Утилита для работы со строками.
 */
public class StringUtil {

    private StringUtil() {

    }

    /**
     * Возвращает новую строку, состоящую из элементов списка, разделенных разделителем.
     *
     * @param delimiter разделитель.
     * @param longList  список чисел.
     * @return строка, состоящая из элементов списка, разделенных разделителем.
     */
    public String join(String delimiter,List<Long> longList) {
        List<String> stringList = longList.stream()
                .map(String::valueOf)
                .toList();
        return String.join(delimiter, stringList);
    }
}
