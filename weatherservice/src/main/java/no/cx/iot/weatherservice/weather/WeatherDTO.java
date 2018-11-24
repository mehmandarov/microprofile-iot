package no.cx.iot.weatherservice.weather;

public class WeatherDTO {
    private final String temperature;

    public WeatherDTO(Temperature temperature) {
        this.temperature = temperature.getTemperature();
    }
}
