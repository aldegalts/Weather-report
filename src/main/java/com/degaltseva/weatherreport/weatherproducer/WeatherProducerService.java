package com.degaltseva.weatherreport.weatherproducer;

import com.degaltseva.weatherreport.model.WeatherData;
import com.degaltseva.weatherreport.model.WeatherGenerator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherProducerService {

    private final KafkaTemplate<String, WeatherData> kafkaTemplate;
    private final WeatherGenerator generator = new WeatherGenerator();


    public WeatherProducerService(KafkaTemplate<String, WeatherData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void sendWeather() {
        kafkaTemplate.send("weather", generator.generateWeather());
    }
}
