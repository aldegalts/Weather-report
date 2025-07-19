package com.degaltseva.weatherreport.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WeatherGenerator {
    private final List<String> cityNames = Arrays.asList("Москва", "Санкт-Петербург", "Воронеж", "Тула", "Омск", "Тамбов");
    private final Random random = new Random();

    public WeatherData generateWeather() {
        return new WeatherData(
                generateCityName(),
                generateDate(),
                generateTemperature(),
                generateWeatherCondition()
        );
    }

    private String generateCityName() {
        return cityNames.get(random.nextInt(cityNames.size()));
    }

    private LocalDate generateDate() {
        int year = 2020 + random.nextInt(5);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28); // 28, чтобы не учитывать длину месяца

        return LocalDate.of(year, month, day);
    }

    private int generateTemperature() {
        return random.nextInt(35) + 1;
    }

    private WeatherCondition generateWeatherCondition() {
        WeatherCondition[] weatherCondition = WeatherCondition.values();

        return weatherCondition[random.nextInt(weatherCondition.length)];
    }

}
