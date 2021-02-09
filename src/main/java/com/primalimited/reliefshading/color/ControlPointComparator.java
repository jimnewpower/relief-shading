package com.primalimited.reliefshading.color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class ControlPointComparator implements java.util.Comparator<ControlPoint> {
    static List<ControlPoint> getSortedList(ControlPoint...controlPoints) {
        return Collections.unmodifiableList(
                Arrays
                    .stream(controlPoints)
                    .sorted(new ControlPointComparator())
                    .collect(Collectors.toList())
        );
    }

    @Override
    public int compare(ControlPoint cp0, ControlPoint cp1) {
        return Integer.compare(cp0.index(), cp1.index());
    }
}
