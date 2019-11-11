package com.anhtu;

import java.util.List;
import java.util.concurrent.*;

public class App {
    private Geometry cityPoint ;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        App app = new App();
        ReadInput readInput = new ReadInput();
        Solver solver = new Solver();
        DownloadData downloadData = new DownloadData();

        downloadData.downloadDataAsync();
        app.setCityPoint(readInput.readInput());

        List<Feature> earthquakes = solver.solve(downloadData.getEarthquakeCollection().get().getFeatures(), app.getCityPoint());

        earthquakes.forEach(earthquake -> System.out.println(earthquake.getProperties().getTitle() + " || "
                            + Math.round(Solver.calculateDistanceInKilometer(app.getCityPoint().getCoordinates().get(1), app.getCityPoint().getCoordinates().get(0),
                                                                             earthquake.getGeometry().getCoordinates().get(1), earthquake.getGeometry().getCoordinates().get(0)))));
    }

    private Geometry getCityPoint() {
        return cityPoint;
    }

    private void setCityPoint(Geometry cityPoint) {
        this.cityPoint = cityPoint;
    }
}
