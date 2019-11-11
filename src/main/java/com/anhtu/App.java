package com.anhtu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {
    private static final String URL_DATA_EARTHEARTHQUAKES = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    private static final Geometry cityPoint = new Geometry();
    private static Future<FeatureCollection> earthquakeCollection;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        downloadDataAsync();
        readInput();

        Solver solver = new Solver();
        List<Feature> earthquakes = solver.solve(earthquakeCollection.get().getFeatures(), cityPoint);

        earthquakes.forEach(earthquake -> System.out.println(earthquake.getProperties().getTitle() + " || "
                            + Math.round(Solver.calculateDistanceInKilometer(cityPoint.getCoordinates().get(1), cityPoint.getCoordinates().get(0),
                                                                             earthquake.getGeometry().getCoordinates().get(1), earthquake.getGeometry().getCoordinates().get(0)))));
    }

    private static void downloadDataAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        earthquakeCollection = executorService.submit(() -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(new URL(URL_DATA_EARTHEARTHQUAKES), FeatureCollection.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    private static void readInput() {
        Scanner scan = new Scanner(System.in);

        double cityLatitude = scan.nextFloat();
        double cityLongitude = scan.nextFloat();

        cityPoint.getCoordinates().add(cityLongitude);
        cityPoint.getCoordinates().add(cityLatitude);
    }
}
