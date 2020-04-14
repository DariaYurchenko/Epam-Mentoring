package com.epam.exercises.cleaningcode.exercise1;

import java.util.List;

public class Segment {
    private List<Integer> intensity;

    public Segment(List<Integer> intensity) {
        this.intensity = intensity;
    }

    public List<Integer> getIntensity() {
        return intensity;
    }

}
