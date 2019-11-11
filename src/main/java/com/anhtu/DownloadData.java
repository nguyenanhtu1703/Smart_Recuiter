package com.anhtu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadData {
    private Future<FeatureCollection> earthquakeCollection;

    void downloadDataAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        earthquakeCollection = executorService.submit(() -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                return mapper.readValue(new URL(Configuration.URL_DATA_EARTHEARTHQUAKES), FeatureCollection.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    Future<FeatureCollection> getEarthquakeCollection() {
        return earthquakeCollection;
    }
}
