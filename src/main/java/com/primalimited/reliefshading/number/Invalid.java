package com.primalimited.reliefshading.number;

import java.util.function.DoublePredicate;

/**
 * Grid files usually support the concept of a "non-existant" value.
 * Because grid files store all of their values as numbers, any
 * missing data must be represented by a known numeric value.
 */
public interface Invalid {
    double INVALID_DOUBLE = Double.MAX_VALUE;
    float INVALID_FLOAT = Float.MAX_VALUE;
    int INVALID_INT = Integer.MAX_VALUE;
    short INVALID_SHORT = Short.MAX_VALUE;

    DoublePredicate VALID_DOUBLE = (x) -> !test(x);

    Invalid DOUBLE_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            double doubleValue = number.doubleValue();
            return Double.isInfinite(doubleValue)
                    || Double.isNaN(doubleValue)
                    || Double.compare(INVALID_DOUBLE, doubleValue) == 0;
        }
    };

    Invalid FLOAT_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            float value = number.floatValue();
            return Float.isInfinite(value)
                    || Float.isNaN(value)
                    || Float.compare(INVALID_FLOAT, value) == 0;
        }
    };

    Invalid INT_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            int value = number.intValue();
            return INVALID_INT == value;
        }
    };

    Invalid SHORT_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            short value = number.shortValue();
            return INVALID_SHORT == value;
        }
    };

    static Invalid doubleInstance() {
        return DOUBLE_INSTANCE;
    }

    static Invalid floatInstance() {
        return FLOAT_INSTANCE;
    }

    static Invalid intInstance() {
        return INT_INSTANCE;
    }

    static Invalid shortInstance() {
        return SHORT_INSTANCE;
    }

    static boolean test(Number number) {
        if (number == null)
            return true;

        if (number instanceof Double)
            return doubleInstance().invalid(number);

        if (number instanceof Float)
            return floatInstance().invalid(number);

        if (number instanceof Integer)
            return intInstance().invalid(number);

        if (number instanceof Short)
            return shortInstance().invalid(number);

        throw new IllegalArgumentException("Number type " + number.getClass().getSimpleName() + " not supported.");
    }

    default boolean invalid(Number number) {
        return test(number);
    }
}
