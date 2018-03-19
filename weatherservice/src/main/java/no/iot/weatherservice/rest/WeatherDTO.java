package no.iot.weatherservice.rest;

import lombok.Data;

@Data
public class WeatherDTO {
    private final String temperature;

    public WeatherDTO(String temperature) {
        this.temperature = temperature;
    }
}
