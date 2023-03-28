package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.number.Invalid;
import com.primalimited.reliefshading.prefs.Preferences;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementation based on:
 * Burrough, P. A. and McDonell, R. A., 1998. Principles of Geographical Information
 * Systems (Oxford University Press, New York), 190 pp.
 */
class ReliefShaderImpl implements ReliefShader {
    private static final int N_SURROUNDING_CELLS = 8;
    private static final int UNSIGNED_BYTE_MAX = 255;
    private static final double DEFAULT_Z = 0.5;
    private static final double RADIANS_TO_DEGREES = 180 / Math.PI;
    public static final double DEGREES_TO_RADIANS = Math.PI / 180;

    static ReliefShader create(Preferences preferences) {
        return new ReliefShaderImpl(preferences);
    }

    private final transient double azimuthRadians;
    private final transient double altitudeRadians;

    ReliefShaderImpl(Preferences preferences) {
        Objects.requireNonNull(preferences, "preferences");
        this.azimuthRadians = Math.toRadians(preferences.azimuthDegrees());
        this.altitudeRadians = Math.toRadians(preferences.altitudeDegrees());
    }

    /**
     * Apply relief shading algorithm to the input grid, returning a result
     * grid of the same dimensions, but with z values representing grayscale
     * relief shading values.
     *
     * @param grid input grid
     * @param zFactor conversion factor that adjusts the units of measure for
     *                the elevation units when they differ from the horizontal units
     * @return result grid
     */
    @Override
    public Grid apply(Grid grid, Float zFactor) {
        Objects.requireNonNull(grid, "grid");

        double meanExtents = mean(grid.bounds().width(), grid.bounds().height());
        if (Double.compare(0.0, meanExtents) == 0)
            return grid;

        Bounds zBounds = grid.zBounds();
        if (zBounds.rangeIsZero())
            return grid;

        double sinTheta = Math.sin(altitudeRadians);
        double cosTheta = Math.cos(altitudeRadians);

        double gridRes = mean(
                grid.bounds().width() / grid.columns(),
                grid.bounds().height() / grid.rows()
        );
        double gridRes8 = gridRes * N_SURROUNDING_CELLS;

        int size = grid.rows() * grid.columns();
        Short[] values = new Short[size];
        Arrays.fill(values, (short)0);

        short missingZ = 0;

        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
        double[] neighbor = new double[N_SURROUNDING_CELLS];

        for (int index = 0; index < size; index++) {
            int row = grid.rowFromCellIndex(index);
            int col = grid.columnFromCellIndex(index);

            Number gridZ = grid.value(row, col);
            if (Invalid.test(gridZ)) {
                values[index] = missingZ;
                continue;
            }

            double adjustedElevation = gridZ.doubleValue() * zFactor;

            // neighboring cells
            for (int c = 0; c < 8; c++) {
                neighbor[c] = adjustedElevation;

                int neighborRow = row + dy[c];
                if (neighborRow < 0 || neighborRow >= grid.rows())
                    continue;

                int neighborCol = col + dx[c];
                if (neighborCol < 0 || neighborCol >= grid.columns())
                    continue;

                Number value = grid.value(neighborRow, neighborCol);
                if (Invalid.test(value))
                    continue;

                neighbor[c] = value.doubleValue() * zFactor;
            }

            double zValue = DEFAULT_Z;

            // slope and aspect
            // Burrough, P. A. and McDonell, R. A., 1998. Principles of Geographical Information
            double fx = (neighbor[2] - neighbor[4] + 2 * (neighbor[1] - neighbor[5]) + neighbor[0] - neighbor[6]) / gridRes8;
            double fy = (neighbor[6] - neighbor[4] + 2 * (neighbor[7] - neighbor[3]) + neighbor[0] - neighbor[2]) / gridRes8;
            if (Double.compare(0.0, fx) != 0) {
                // slope
                double tanSlope = Math.sqrt(fx * fx + fy * fy);
                // aspect
                double aspect = (180 - Math.atan(fy / fx)
                        * RADIANS_TO_DEGREES
                        + 90 * (fx / Math.abs(fx))) * DEGREES_TO_RADIANS;
                // z value
                double term1 = tanSlope / Math.sqrt(1 + tanSlope * tanSlope);
                double term2 = sinTheta / tanSlope;
                double term3 = cosTheta * Math.sin(azimuthRadians - aspect);
                zValue = term1 * (term2 - term3);
            }

            // scale to 0-255
            int value = (int) Math.round(zValue * UNSIGNED_BYTE_MAX);
            value = (int) Bounds.RGB_8_BIT.constrain(value);
            values[index] = (short) value;
        }

        return Grid.createRowMajorSWOriginWithZBounds(
                grid.rows(),
                grid.columns(),
                grid.bounds(),
                zBounds,
                values
        );
    }

    private double mean(double a, double b) {
        return (a + b) / 2.0;
    }
}
