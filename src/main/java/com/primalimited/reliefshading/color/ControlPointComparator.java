package com.primalimited.reliefshading.color;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Compares control points by their index.
 */
class ControlPointComparator implements java.util.Comparator<ControlPoint> {
    /**
     * From an array of control points, build a sorted list of control points.
     * Note that if any control points have been defined with the same index,
     * they will be considered duplicates, and only 1 will be kept.
     *
     * @param controlPoints array of control points
     * @return an unmodifiable sorted list of control points
     */
    static List<ControlPoint> getSortedList(ControlPoint...controlPoints) {
        SortedSet<ControlPoint> sortedSet = new TreeSet<>(new ControlPointComparator());
        sortedSet.addAll(Arrays.asList(controlPoints));
        return Collections.unmodifiableList(sortedSet.stream().collect(Collectors.toList()));
    }

    @Override
    public int compare(ControlPoint cp0, ControlPoint cp1) {
        return Integer.compare(cp0.index(), cp1.index());
    }
}
