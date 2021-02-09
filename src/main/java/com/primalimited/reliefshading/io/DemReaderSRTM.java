package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import com.primalimited.reliefshading.grid.Grid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Objects;

class DemReaderSRTM implements DemReader {
    private static final int N_ROWS = 3601;
    private static final int N_COLS = 3601;
    private static final int SIZE = N_ROWS * N_COLS;
    private static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    private transient final Path path;

    DemReaderSRTM(Path path) {
        this.path = Objects.requireNonNull(path, "Path for DEM file.");
    }

    @Override
    public Grid read() {
        Bounds2D bounds = createBounds();
        Short[] values = new Short[SIZE];

        try (FileChannel fileChannel = new FileInputStream(path.toFile()).getChannel()) {
            fileChannel.position(0L);

            ByteBuffer byteBuffer = allocate();

            fileChannel.read(byteBuffer, 0L);

            for (int index = 0; index < SIZE; index++)
                values[index] = byteBuffer.getShort(index * Short.BYTES);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Grid.createRowMajorSWOrigin(N_ROWS, N_COLS, bounds, values);
    }

    private ByteBuffer allocate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(nBytes());
        byteBuffer.order(BYTE_ORDER);
        return byteBuffer;
    }

    private int nBytes() {
        return Short.BYTES * SIZE;
    }

    Bounds2D createBounds() {
        return Bounds2D.create(getLongitudeBounds(), getLatitudeBounds());
    }

    Bounds getLongitudeBounds() {
        // Filename longitude is for west edge of quad, so add 1 for max
        int longitude = getLongitude();
        return Bounds.of(longitude, longitude + 1.0);
    }

    Bounds getLatitudeBounds() {
        // Filename latitude is for south edge of quad, so add 1 for max
        int latitude = getLatitude();
        return Bounds.of(latitude, latitude + 1.0);
    }

    int getLongitude() {
        String[] split = filename().split(getLatitudeText());
        split = split[1].split(getLongitudeText());
        String str = split[1].substring(0, split[1].indexOf("."));
        int longitude = Integer.parseInt(str);
        if (isWest())
            longitude = -longitude;
        return longitude;
    }

    int getLatitude() {
        String str = filename().split(getLatitudeText())[1].split(getLongitudeText())[0];
        int latitude = Integer.parseInt(str);
        if (isSouth())
            latitude = -latitude;
        return latitude;
    }

    String getLatitudeText() {
        return isNorth() ? "N" : "S";
    }

    String getLongitudeText() {
        return isWest() ? "W" : "E";
    }

    boolean isNorth() {
        return filename().contains("N");
    }

    boolean isSouth() {
        return filename().contains("S");
    }

    boolean isEast() {
        return filename().contains("E");
    }

    boolean isWest() {
        return filename().contains("W");
    }

    String filename() {
        return path.getFileName().toString().toUpperCase();
    }
}
