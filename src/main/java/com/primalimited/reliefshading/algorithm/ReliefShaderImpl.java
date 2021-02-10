package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.number.Invalid;
import com.primalimited.reliefshading.prefs.Preferences;

import java.util.Arrays;
import java.util.Objects;

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

    private transient double meanExtents;
    private transient double zRange;
    private transient double zFactor = 1.0;
    private transient double sinTheta;
    private transient double cosTheta;
    private transient double gridRes;
    private transient double gridRes8;

    private transient Short[] values;

    ReliefShaderImpl(Preferences preferences) {
        Objects.requireNonNull(preferences, "preferences");
        this.azimuthRadians = Math.toRadians(preferences.azimuthDegrees());
        this.altitudeRadians = Math.toRadians(preferences.altitudeDegrees());
    }

    @Override
    public Grid apply(Grid grid) {
        Objects.requireNonNull(grid, "grid");

        meanExtents = mean(grid.bounds().width(), grid.bounds().height());
        if (Double.compare(0.0, meanExtents) == 0)
            return grid;

        Bounds zBounds = grid.zBounds();
        if (zBounds.rangeIsZero())
            return grid;

        zRange = zBounds.range();
        zFactor = meanExtents > zRange
                ? meanExtents / zRange
                : zRange / meanExtents;

        sinTheta = Math.sin(altitudeRadians);
        cosTheta = Math.cos(altitudeRadians);

        gridRes = mean(
                grid.bounds().width()/grid.columns(),
                grid.bounds().height()/grid.rows()
        );
        gridRes8 = gridRes * N_SURROUNDING_CELLS;

        int size = grid.rows() * grid.columns();
        values = new Short[size];
        Arrays.fill(values, (short)0);

        short missingZ = 0;

        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
        double[] N = new double[N_SURROUNDING_CELLS];

        double zFraction = 0.0;

        for (int index = 0; index < size; index++) {
            int row = grid.rowFromCellIndex(index);
            int col = grid.columnFromCellIndex(index);

            Number gridZ = grid.value(row, col);
            if (Invalid.test(gridZ)) {
                values[index] = missingZ;
                continue;
            }

            double z = gridZ.doubleValue();

            // neighboring cells
            for (int c = 0; c < 8; c++) {
                N[c] = z;

                int neighborRow = row + dy[c];
                if (neighborRow < 0 || neighborRow >= grid.rows())
                    continue;

                int neighborCol = col + dx[c];
                if (neighborCol < 0 || neighborCol >= grid.columns())
                    continue;

                Number value = grid.value(neighborRow, neighborCol);
                if (Invalid.test(value))
                    continue;

                N[c] = value.doubleValue() * zFactor;
            }

            // slope and aspect
            double fx = (N[2] - N[4] + 2 * (N[1] - N[5]) + N[0] - N[6]) / gridRes8;
            double fy = (N[6] - N[4] + 2 * (N[7] - N[3]) + N[0] - N[2]) / gridRes8;
            if (Double.compare(0.0, fx) != 0) {
                double tanSlope = Math.sqrt(fx * fx + fy * fy);
                double aspect = (180 - Math.atan(fy / fx)
                        * RADIANS_TO_DEGREES
                        + 90 * (fx / Math.abs(fx))) * DEGREES_TO_RADIANS;
                double term1 = tanSlope / Math.sqrt(1 + tanSlope * tanSlope);
                double term2 = sinTheta / tanSlope;
                double term3 = cosTheta * Math.sin(azimuthRadians - aspect);
                z = term1 * (term2 - term3);
            } else {
                z = DEFAULT_Z;
            }

            z = Bounds.RGB_8_BIT.bind(Math.round(z * UNSIGNED_BYTE_MAX));
            values[index] = (short) Math.round(z);
        }

        Grid result = Grid.createRowMajorSWOriginWithZBounds(
                grid.rows(),
                grid.columns(),
                grid.bounds(),
                zBounds,
                values
        );
        return result;
    }

    private double mean(double a, double b) {
        return (a + b) / 2.0;
    }
}
