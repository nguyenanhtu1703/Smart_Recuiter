package com.anhtu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {
    private static String URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    private static float cityLatitude, cityLongitude;
    private static Future<FeatureCollection> earthquakeCollection;
    private final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        downloadDataAsync();
        readInput();

        Feature[] earthquakes = earthquakeCollection.get().features;

        Arrays.sort(earthquakes, (o1, o2) -> {
            double d1 = calculateDistanceInKilometer(cityLatitude, cityLongitude, o1.getGeometry().getCoordinates()[1], o1.getGeometry().getCoordinates()[0]);
            double d2 = calculateDistanceInKilometer(cityLatitude, cityLongitude, o2.getGeometry().getCoordinates()[1], o2.getGeometry().getCoordinates()[0]);

            return (int) (d1 - d2);
        });

        int count10Earthquakes = 0;

        // list 10 earthquakes on the terminal, 2 earthquakes with same lat/lon will be printed only one
        for (int i = 0; i < earthquakes.length; i++) {
            if (i > 0 && earthquakes[i].getGeometry().equals(earthquakes[i - 1].getGeometry()))
                continue;

            count10Earthquakes++;
            System.out.println(earthquakes[i].getProperties().getTitle() + " || " + Math.round(calculateDistanceInKilometer(cityLatitude, cityLongitude, earthquakes[i].getGeometry().getCoordinates()[1], earthquakes[i].getGeometry().getCoordinates()[0])));
            if (count10Earthquakes >= 10)
                break;
        }
    }

    private static void downloadDataAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        earthquakeCollection = executorService.submit(new Callable<FeatureCollection>() {
            @Override
            public FeatureCollection call() throws Exception {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return mapper.readValue(new URL(URL), FeatureCollection.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            };
        });
    }

    private static void readInput() {
        Scanner scan = new Scanner(System.in);

        cityLatitude = scan.nextFloat();
        cityLongitude = scan.nextFloat();
    }

    public static double calculateDistanceInKilometer(double lat1, double long1, double lat2, double long2) {
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
        ans = ans * AVERAGE_RADIUS_OF_EARTH_KM;

        return ans;
        //return org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2) / 1000;
    }
}
