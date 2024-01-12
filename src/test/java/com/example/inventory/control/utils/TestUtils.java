package com.example.inventory.control.utils;

public final class TestUtils {

    private TestUtils() {
    }

    public static Long generatedRandomId() {
        return generatedRandomId(1, Long.MAX_VALUE);
    }

    public static Long generatedRandomId(long minValue, long maxValue) {
        return minValue + (long) (Math.random() * (maxValue - minValue + 1));
    }
}
