package com.primalimited.reliefshading.number;

import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public interface Invalid {
    public static final double INVALID_DOUBLE = Double.MAX_VALUE;
    public static final float INVALID_FLOAT = Float.MAX_VALUE;
    public static final int INVALID_INT = Integer.MAX_VALUE;
    public static final short INVALID_SHORT = Short.MAX_VALUE;

    public static DoublePredicate VALID_DOUBLE = (x) -> !test(x);

    static final Invalid DOUBLE_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            return test(number);
        }
    };

    public static Invalid doubleInstance() {
        return DOUBLE_INSTANCE;
    }

    static boolean test(Number number) {
        if (number == null)
            return true;

        if (number instanceof Double) {
            double doubleValue = number.doubleValue();
            return Double.isInfinite(doubleValue)
            || Double.isNaN(doubleValue)
            || Double.compare(INVALID_DOUBLE, doubleValue) == 0;
        }

        if (number instanceof Float)
            return Float.compare(number.floatValue(), INVALID_FLOAT) == 0;

        if (number instanceof Integer)
            return number.intValue() == INVALID_INT;

        if (number instanceof Short)
            return number.shortValue() == INVALID_SHORT;

        return false;
    }

    default boolean invalid(Number number) {
        return test(number);
    }
}
