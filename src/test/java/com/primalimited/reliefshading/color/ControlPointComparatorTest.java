package com.primalimited.reliefshading.color;

import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ControlPointComparatorTest {
    @Test
    public void compareSame() {
        ControlPoint cp0 = new ControlPoint(0, 0);
        ControlPoint cp1 = new ControlPoint(0, 24352);
        assertEquals(0, new ControlPointComparator().compare(cp0, cp1));
    }

    @Test
    public void compareDifferent() {
        ControlPoint cp0 = new ControlPoint(0, 0);
        ControlPoint cp1 = new ControlPoint(1, 24352);
        assertEquals(-1, new ControlPointComparator().compare(cp0, cp1));
        assertEquals(1, new ControlPointComparator().compare(cp1, cp0));
    }

    @Test
    public void testComparator() {
        ControlPoint cp0 = new ControlPoint(0, 0);
        ControlPoint cp1 = new ControlPoint(1, 0);
        SortedSet<ControlPoint> sortedSet = new TreeSet<>(new ControlPointComparator());
        sortedSet.add(cp1);
        sortedSet.add(cp0);
        assertEquals(0, sortedSet.first().index());
        assertEquals(1, sortedSet.last().index());
    }

    @Test
    public void getSortedListFromArray() {
        ControlPoint cp0 = new ControlPoint(1, 0);
        ControlPoint cp1 = new ControlPoint(0, 0);
        ControlPoint cp2 = new ControlPoint(2, 0);

        List<ControlPoint> sortedList = ControlPointComparator
                .getSortedList(cp0, cp1, cp2);

        int listIndex = 0;
        assertEquals(0, sortedList.get(listIndex++).index());
        assertEquals(1, sortedList.get(listIndex++).index());
        assertEquals(2, sortedList.get(listIndex++).index());
    }

    @Test
    public void duplicateIndexes() {
        ControlPoint cp0 = new ControlPoint(0, 0);
        ControlPoint cp1 = new ControlPoint(0, -928734);
        ControlPoint cp2 = new ControlPoint(2, 0);
        ControlPoint cp3 = new ControlPoint(2, 3793);

        List<ControlPoint> sortedList = ControlPointComparator
                .getSortedList(cp0, cp1, cp2, cp3);

        assertEquals(2, sortedList.size(), "list size");
    }
}