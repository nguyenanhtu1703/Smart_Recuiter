package com.anhtu;


import java.util.ArrayList;
import java.util.List;

class Solver {
    private static final double RADIUS_OF_EARTH_KM = 6371;

    List<Feature> solve(List<Feature> earthquakes, Geometry cityPoint) {
        List<Feature> earthquakesResult = new ArrayList<>();

        earthquakes.sort((o1, o2) -> {
            double d1 = calculateDistanceInKilometer(cityPoint.getCoordinates().get(1), cityPoint.getCoordinates().get(0),
                                                     o1.getGeometry().getCoordinates().get(1), o1.getGeometry().getCoordinates().get(0));
            double d2 = calculateDistanceInKilometer(cityPoint.getCoordinates().get(1), cityPoint.getCoordinates().get(0),
                                                     o2.getGeometry().getCoordinates().get(1), o2.getGeometry().getCoordinates().get(0));

            return (int) (d1 - d2);
        });

        int count10Earthquakes = 0;

        // list 10 earthquakes on the terminal, 2 earthquakes with same lat/lon will be printed only one
        for (int i = 0; i < earthquakes.size(); i++) {
            if (i > 0 && earthquakes.get(i).getGeometry().equals(earthquakes.get(i - 1).getGeometry()))
                continue;

            count10Earthquakes++;
            earthquakesResult.add(earthquakes.get(i));
            if (count10Earthquakes >= 10)
                break;
        }

        return earthquakesResult;
    }


    static double calculateDistanceInKilometer(double lat1, double long1, double lat2, double long2) {
        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);

        // Haversine Formula
        double dlong = long2 - long1;
        double dlat = lat2 - lat1;

        double ans = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dlong / 2), 2);

        ans = 2 * Math.asin(Math.sqrt(ans));

        // Calculate the result
        ans = ans * RADIUS_OF_EARTH_KM;

        return ans;
        //return org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2) / 1000;
    }
}
