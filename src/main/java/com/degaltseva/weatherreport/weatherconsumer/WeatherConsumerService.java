package com.degaltseva.weatherreport.weatherconsumer;

import com.degaltseva.weatherreport.model.WeatherCondition;
import com.degaltseva.weatherreport.model.WeatherData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherConsumerService {
    private Map<String, List<WeatherData>> weatherDataMap = new HashMap<>();

    @KafkaListener(topics = "weather", groupId = "weather-consumer-group")
    public void consumeWeatherReport(WeatherData weatherData) {
        if (!weatherDataMap.containsKey(weatherData.getCity())) {
            List<WeatherData> weatherDataList = new ArrayList<>();
            weatherDataList.add(weatherData);
            weatherDataMap.put(weatherData.getCity(), weatherDataList);
        } else {
            weatherDataMap.get(weatherData.getCity()).add(weatherData);
        }

        System.out.println("Consumed: " + weatherData.getCity());
    }

    public List<WeatherData> getWeatherData(String city) throws Exception {
        if (weatherDataMap.containsKey(city)) {
            return weatherDataMap.get(city);
        } else {
            throw new Exception("Данный город отсутствует в базе");
        }
    }

    public List<WeatherData> getAllSunnyDays() {
        return getWeatherDataByCondition(WeatherCondition.Солнечно);
    }

    public List<WeatherData> getAllCloudyDays() {
        return getWeatherDataByCondition(WeatherCondition.Облачно);
    }

    public List<WeatherData> getAllRainyDays() {
        return getWeatherDataByCondition(WeatherCondition.Дождь);
    }

    private List<WeatherData> getWeatherDataByCondition(WeatherCondition condition) {
        List<WeatherData> result = new ArrayList<>();
        for(Map.Entry<String, List<WeatherData>> data : weatherDataMap.entrySet()) {
            for (WeatherData weatherData : data.getValue()) {
                if (weatherData.getWeatherCondition() == condition) {
                    result.add(weatherData);
                }
            }
        }
        return result;
    }
}
