package com.anhtu;

public class Geometry {
    String type;
    double[] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Geometry == false)
            return false;

        Geometry geometry = (Geometry) obj;
        return coordinates[0] == geometry.getCoordinates()[0] && coordinates[1] == geometry.getCoordinates()[1];
    }
}
