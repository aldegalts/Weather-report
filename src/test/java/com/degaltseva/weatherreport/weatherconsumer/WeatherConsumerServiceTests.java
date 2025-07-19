package com.degaltseva.weatherreport.weatherconsumer;

import com.degaltseva.weatherreport.model.WeatherCondition;
import com.degaltseva.weatherreport.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WeatherConsumerServiceTests {

    private WeatherConsumerService service;

    @BeforeEach
    void setUp() {
        service = new WeatherConsumerService();
    }

    @Test
    void should_add_new_city_weather_data() throws Exception {
        WeatherData data = new WeatherData("Москва", LocalDate.now(), 20, WeatherCondition.Солнечно);
        service.consumeWeatherReport(data);

        List<WeatherData> result = service.getWeatherData("Москва");
        assertThat(result).containsExactly(data);
    }

    @Test
    void should_append_weather_data_to_existing_city() throws Exception {
        WeatherData data1 = new WeatherData("Казань", LocalDate.now(), 18, WeatherCondition.Дождь);
        WeatherData data2 = new WeatherData("Казань", LocalDate.now().plusDays(1), 17, WeatherCondition.Облачно);

        service.consumeWeatherReport(data1);
        service.consumeWeatherReport(data2);

        List<WeatherData> result = service.getWeatherData("Казань");
        assertThat(result).containsExactly(data1, data2);
    }

    @Test
    void should_throw_exception_when_city_not_found() {
        assertThatThrownBy(() -> service.getWeatherData("НеизвестныйГород"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("отсутствует в базе");
    }

    @Test
    void should_return_only_sunny_days() {
        WeatherData sunny = new WeatherData("Тула", LocalDate.now(), 25, WeatherCondition.Солнечно);
        WeatherData cloudy = new WeatherData("Тула", LocalDate.now(), 19, WeatherCondition.Облачно);

        service.consumeWeatherReport(sunny);
        service.consumeWeatherReport(cloudy);

        List<WeatherData> result = service.getAllSunnyDays();
        assertThat(result).containsExactly(sunny);
    }

    @Test
    void should_return_only_cloudy_days_from_multiple_cities() {
        WeatherData data1 = new WeatherData("Тверь", LocalDate.now(), 15, WeatherCondition.Облачно);
        WeatherData data2 = new WeatherData("Ярославль", LocalDate.now(), 14, WeatherCondition.Дождь);
        WeatherData data3 = new WeatherData("Воронеж", LocalDate.now(), 16, WeatherCondition.Облачно);

        service.consumeWeatherReport(data1);
        service.consumeWeatherReport(data2);
        service.consumeWeatherReport(data3);

        List<WeatherData> result = service.getAllCloudyDays();
        assertThat(result).containsExactlyInAnyOrder(data1, data3);
    }

    @Test
    void should_return_empty_list_when_no_rainy_days() {
        WeatherData data = new WeatherData("Сочи", LocalDate.now(), 30, WeatherCondition.Солнечно);
        service.consumeWeatherReport(data);

        List<WeatherData> result = service.getAllRainyDays();
        assertThat(result).isEmpty();
    }
}
