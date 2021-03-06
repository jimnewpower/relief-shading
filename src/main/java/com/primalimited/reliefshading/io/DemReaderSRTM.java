package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.number.Invalid;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Objects;

class DemReaderSRTM<T extends Number> implements DemReader {
    private static final int N_ROWS = 3601;
    private static final int N_COLS = 3601;
    private static final int SIZE = N_ROWS * N_COLS;
    private static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    private transient final Path path;
    private transient final FilenameSRTM filename;

    DemReaderSRTM(Path path) {
        this.path = Objects.requireNonNull(path, "Path for DEM file.");
        this.filename = FilenameSRTM.create(path.getFileName().toString());
    }

    @Override
    public Grid read() throws IOException {
        double min = (double)Short.MAX_VALUE;
        double max = (double)Short.MIN_VALUE;

        Short[] values = new Short[SIZE];

        try (FileChannel fileChannel = new FileInputStream(path.toFile()).getChannel()) {
            fileChannel.position(0L);

            ByteBuffer byteBuffer = allocate();

            fileChannel.read(byteBuffer, 0L);

            for (int index = 0; index < SIZE; index++) {
                short value = byteBuffer.getShort(index * Short.BYTES);
                values[index] = value;

                if (!Invalid.shortInstance().invalid(values[index])) {
                    min = Math.min(min, value);
                    max = Math.max(max, value);
                }
            }
        }

        Bounds zBounds = Bounds.valid(min, max)
            ? Bounds.of(min, max)
                : Bounds.nullBounds();

        Short[] flipped = new Short[SIZE];
        flipNorthSouth(values, flipped);

        return Grid.createRowMajorSWOriginWithZBounds(
                N_ROWS,
                N_COLS,
                filename.createBounds(),
                zBounds,
                flipped
        );
    }

    private void flipNorthSouth(Short[] values, Short[] flipped) {
        for (int row = 0; row < N_ROWS; row++) {
            int srcPos = row * N_COLS;
            int destPos = (N_ROWS - 1 - row) * N_COLS;
            System.arraycopy(values, srcPos, flipped, destPos, N_COLS);
        }
    }

    private ByteBuffer allocate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(nBytes());
        byteBuffer.order(BYTE_ORDER);
        return byteBuffer;
    }

    private int nBytes() {
        return Short.BYTES * SIZE;
    }
}
