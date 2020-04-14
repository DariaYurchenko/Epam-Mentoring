package com.epam.exercises.cleaningcode.exercise1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.epam.exercises.cleaningcode.exercise1.exceptions.InvalidCoordinateException;

public class Image {
    private static final int LAST_BYTE = 0xFF;
    private static final int BYTE = 8;
    private static final int TWO_BYTES = 16;

    private BufferedImage image;

    public Image(String fileName) throws IOException {
        this.image = loadImageFromFile(fileName);
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getIntensity(Coordinate coordinate) {
        return getRed(coordinate) + getBlue(coordinate) + getGreen(coordinate);
    }

    private int getRed(Coordinate coordinate) {
        return getRgbValue(coordinate) >> TWO_BYTES;
    }

    private int getGreen(Coordinate coordinate) {
        return getRgbValue(coordinate) >> BYTE;
    }

    private int getBlue(Coordinate coordinate) {
        return getRgbValue(coordinate) & LAST_BYTE;
    }

    private int getRgbValue(Coordinate coordinate) {
        if (coordinate.getX() < 0 || coordinate.getX() > image.getWidth()) {
            throw new InvalidCoordinateException("Coordinate x out of range: 0.." + image.getWidth());
        } else if (coordinate.getY() < 0 || coordinate.getY() > image.getHeight()) {
            throw new InvalidCoordinateException("Coordinate y out of range: 0.." + image.getHeight());
        }
        return image.getRGB(coordinate.getX(), coordinate.getY());
    }

    private BufferedImage loadImageFromFile(String fileName) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)));
    }

}
