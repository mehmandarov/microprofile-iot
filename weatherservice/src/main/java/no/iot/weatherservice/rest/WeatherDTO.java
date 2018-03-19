package no.iot.weatherservice.rest;

import lombok.Data;

@Data
public class WeatherDTO {
    private final String weatherRepresentation;

    public WeatherDTO(String localTemperature) {
        weatherRepresentation = localTemperature.toString();
    }
}
