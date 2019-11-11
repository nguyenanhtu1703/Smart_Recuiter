package com.anhtu;

import java.util.Scanner;

class ReadInput {
    Geometry readInput() {
        Scanner scan = new Scanner(System.in);

        double cityLatitude = scan.nextFloat();
        double cityLongitude = scan.nextFloat();

        Geometry geometry = new Geometry();
        geometry.getCoordinates().add(cityLongitude);
        geometry.getCoordinates().add(cityLatitude);

        return  geometry;
    }
}
