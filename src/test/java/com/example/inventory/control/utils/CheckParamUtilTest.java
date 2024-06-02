package com.example.inventory.control.utils;

import com.example.inventory.control.exceptions.EmptyException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckParamUtilTest {

    @Test
    void isNotBlank() {
        assertThrows(EmptyException.class, () -> CheckParamUtil.isNotBlank("param", ""));
    }

    @Test
    void isNotNull() {
        assertThrows(NullPointerException.class, () -> CheckParamUtil.isNotNull("param", null));
    }

    @Test
    void isNotEmpty() {
        assertThrows(EmptyException.class, () -> CheckParamUtil.isNotEmpty("param", List.of()));
    }

}