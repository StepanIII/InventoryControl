package com.example.inventory.control.utils;

import com.example.inventory.control.exceptions.EmptyException;
import io.micrometer.common.util.StringUtils;

/**
 * Утилита для проверки обязательности параметров.
 */
public final class CheckParamUtil {

    private CheckParamUtil() {

    }

    /**
     * Проверка необходимости строкового параметра.
     *
     * @param param имя параметра.
     * @param value значение параметра.
     */
    public static void isNotBlank(String param, String value) {
        isNotNull(param, value);
        if (StringUtils.isEmpty(value)) {
            throw new EmptyException(String.format("Param %s is empty.", param));
        }
    }

    /**
     * Проверка необходимости ссылочного параметра.
     *
     * @param param имя параметра.
     * @param value значение параметра.
     */
    public static void isNotNull(String param, Object value) {
        if (value == null) {
            throw new NullPointerException(String.format("Param %s is null.", param));
        }
    }

}
