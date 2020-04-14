package com.epam.exercises.cleaningcode.exercise1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedAscii {
    private static final int HEIGHT_DIVISOR = 60;
    private static final int WIDTH_DIVISOR = 200;
    private static final int MAX = 0;
    private static final int MIN = 255 * 3;
    private static final String IMAGE = "pair_hiking.png";
    private static final char[] CHARS_BY_DARKNESS = {'#', '@', 'X', 'L', 'I', ':', '.', ' '};

    private static void printImage(Image image) {
        List<Segment> segments = findSegments(image);
        int maxIntensity = segments.stream().flatMap(segment -> segment.getIntensity().stream()).max(Integer::compareTo).orElse(MAX);
        int minIntensity = segments.stream().flatMap(segment -> segment.getIntensity().stream()).min(Integer::compareTo).orElse(MIN);
        segments.forEach(segment -> printSegment(segment, minIntensity, maxIntensity));
    }

    private static List<Segment> findSegments(Image image) {
        int stepX = image.getWidth() / WIDTH_DIVISOR;
        int stepY = image.getHeight() / HEIGHT_DIVISOR;
        List<Segment> segments = new ArrayList<>();
        for (int y = 0; y < image.getHeight(); y += stepY) {
            List<Integer> segmentIntensities = new ArrayList<>();
            for (int x = 0; x < image.getWidth(); x += stepX) {
                segmentIntensities.add(image.getIntensity(new Coordinate(x, y)));
            }
            segments.add(new Segment(segmentIntensities));
        }
        return segments;
    }

    private static void printSegment(Segment segment, int minIntensity, int maxIntensity) {
        segment.getIntensity().forEach(intensity -> System.out.print(CHARS_BY_DARKNESS[calculateIndexOfChar(intensity, minIntensity, maxIntensity)]));
        System.out.println();
    }

    private static int calculateIndexOfChar(int intensity, int minIntensity, int maxIntensity) {
        return (intensity - minIntensity) * CHARS_BY_DARKNESS.length / (maxIntensity - minIntensity + 1);
    }

    public static void main(String[] args) throws IOException {
        Image image = new Image(IMAGE);
        printImage(image);
    }

}
