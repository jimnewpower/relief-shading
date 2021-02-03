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
            double doubleValue = number.doubleValue();
            return Double.isInfinite(doubleValue)
                    || Double.isNaN(doubleValue)
                    || Double.compare(INVALID_DOUBLE, doubleValue) == 0;
        }
    };

    static final Invalid FLOAT_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            float value = number.floatValue();
            return Float.isInfinite(value)
                    || Float.isNaN(value)
                    || Float.compare(INVALID_FLOAT, value) == 0;
        }
    };

    static final Invalid INT_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            int value = number.intValue();
            return INVALID_INT == value;
        }
    };

    static final Invalid SHORT_INSTANCE = new Invalid() {
        @Override
        public boolean invalid(Number number) {
            short value = number.shortValue();
            return INVALID_SHORT == value;
        }
    };

    public static Invalid doubleInstance() {
        return DOUBLE_INSTANCE;
    }

    public static Invalid floatInstance() {
        return FLOAT_INSTANCE;
    }

    public static Invalid intInstance() {
        return INT_INSTANCE;
    }

    public static Invalid shortInstance() {
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

        return false;
    }

    default boolean invalid(Number number) {
        return test(number);
    }
}
