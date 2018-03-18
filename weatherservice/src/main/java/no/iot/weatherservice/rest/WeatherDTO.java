package no.iot.weatherservice.rest;

import lombok.Data;

@Data
public class WeatherDTO {
    private final String timeRepresentation;

    public WeatherDTO(LocalDateTime localDateTime) {
        timeRepresentation = localDateTime.toString();
    }
}
