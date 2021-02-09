package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

public interface ColorMapper<T> {
    int rgb(T value);

    static ColorMapper<Short> shortInstance(Bounds bounds) {
        return ShortColorMapper.create(bounds);
    }
}
