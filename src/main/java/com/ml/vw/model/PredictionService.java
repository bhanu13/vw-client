package com.ml.vw.model;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class PredictionService {

    @Autowired
    private VWClient vwClient;

    private String file = "/Users/bagarwal/Documents/Ranking/exp1/ranking.train";

    private String samplefeature = "1.0 | Popularity:-0.06921774954032796 Capacity:-0.06332984355193047 ConversionWithPreciseAvailability:-0.014835551223234707 Tags:0.0 Distance:0.016409805936247124 DistancePenalty:0.0 RatingWithCounts:0.03990592910678707 HighPricePenalty:0.0 PopularityNormalizedByCapacity:-0.04123757356185493 Price2:0.0 Price3:0.0 Price4:0.0 PriceRating2:0.029748058319091795 PriceRating3:0.0 PriceRating4:0.0 Distance Log:-0.09716701705591532\n";

    @SneakyThrows
    public PredictionService() {
    }

    @EventListener
    @SneakyThrows
    public void predict(ApplicationReadyEvent event) {
        ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();

        consumerExecutor.submit(() -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String features;
                while ((features = br.readLine()) != null) {
                    features += '\n';
                    vwClient.predict(features);
                }
            } catch (Exception ex) {
                // ToDo: Log exception here
            }
        });
        // ToDo: Log info here
    }
}
