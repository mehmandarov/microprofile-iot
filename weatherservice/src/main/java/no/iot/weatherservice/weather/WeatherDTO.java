package no.iot.weatherservice.weather;

import lombok.Data;

@Data
public class WeatherDTO {
    private final String temperature;

    public WeatherDTO(Temperature temperature) {
        this.temperature = temperature.getTemperature();
    }
}
