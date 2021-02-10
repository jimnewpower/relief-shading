package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;

import java.util.Objects;

public class FilenameSRTM {
    public static FilenameSRTM create(String filename) {
        return new FilenameSRTM(filename);
    }

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final String filename;

    private FilenameSRTM(String filename) {
        this.filename = Objects.requireNonNull(filename);
        if (filename.isBlank())
            throw new IllegalArgumentException("Filename cannot be blank.");
    }

    public Bounds2D createBounds() {
        return Bounds2D.create(getLongitudeBounds(), getLatitudeBounds());
    }

    public Bounds getLongitudeBounds() {
        // Filename longitude is for west edge of quad, so add 1 for max
        int longitude = getLongitude();
        return Bounds.of(longitude, longitude + 1.0);
    }

    public Bounds getLatitudeBounds() {
        // Filename latitude is for south edge of quad, so add 1 for max
        int latitude = getLatitude();
        return Bounds.of(latitude, latitude + 1.0);
    }

    public int getLongitude() {
        String[] split = filename().split(getLatitudeText());
        split = split[1].split(getLongitudeText());
        String str = split[1].substring(0, split[1].indexOf("."));
        int longitude = Integer.parseInt(str);
        if (isWest())
            longitude = -longitude;
        return longitude;
    }

    public int getLatitude() {
        String str = filename().split(getLatitudeText())[1].split(getLongitudeText())[0];
        int latitude = Integer.parseInt(str);
        if (isSouth())
            latitude = -latitude;
        return latitude;
    }

    public String getLatitudeText() {
        return isNorth() ? "N" : "S";
    }

    public String getLongitudeText() {
        return isWest() ? "W" : "E";
    }

    public boolean isNorth() {
        return filename().contains("N");
    }

    public boolean isSouth() {
        return filename().contains("S");
    }

    public boolean isEast() {
        return filename().contains("E");
    }

    public boolean isWest() {
        return filename().contains("W");
    }

    public String filename() {
        return filename;
    }
}
