package com.anhtu;

import java.util.ArrayList;
import java.util.List;

public class Geometry {
    private String type;
    private List<Double> coordinates;

    Geometry() {
        coordinates = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Geometry))
            return false;

        Geometry geometry = (Geometry) obj;
        return coordinates.get(0).equals(geometry.getCoordinates().get(0)) && coordinates.get(0).equals(geometry.getCoordinates().get(0));
    }
}
