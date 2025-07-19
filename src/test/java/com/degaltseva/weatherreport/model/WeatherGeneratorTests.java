package com.degaltseva.weatherreport.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherGeneratorTests {

    @Test
    void generate_should_return_non_null_weatherData() {
        WeatherGenerator weatherGenerator = new WeatherGenerator();
        WeatherData weatherData = weatherGenerator.generateWeather();

        Assert.assertNotNull(weatherData);
        Assert.assertNotNull(weatherData.getCity());
        Assert.assertNotNull(weatherData.getDate());
        Assert.assertNotNull(weatherData.getWeatherCondition());
    }

    @Test
    void generate_should_return_city_from_allowed_list() {
        WeatherGenerator weatherGenerator = new WeatherGenerator();
        WeatherData weatherData = weatherGenerator.generateWeather();

        List<String> allowedCities = List.of("Москва", "Санкт-Петербург", "Воронеж", "Тула", "Омск", "Тамбов");
        assertTrue(allowedCities.contains(weatherData.getCity()));
    }

    @Test
    void generate_should_return_temperature_within_expected_range() {
        WeatherGenerator weatherGenerator = new WeatherGenerator();
        WeatherData weatherData = weatherGenerator.generateWeather();

        assertTrue(weatherData.getTemperature() >= 0 && weatherData.getTemperature() <= 35);
    }

    @Test
    void generate_should_return_valid_weatherCondition() {
        WeatherGenerator weatherGenerator = new WeatherGenerator();
        WeatherData weatherData = weatherGenerator.generateWeather();

        assertTrue(List.of(WeatherCondition.values()).contains(weatherData.getWeatherCondition()));
    }
}
