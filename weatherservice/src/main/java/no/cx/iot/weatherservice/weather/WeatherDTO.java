package no.cx.iot.weatherservice.weather;

public class WeatherDTO {
    private String temperature;

    public WeatherDTO(Temperature temperature) {
        this.temperature = temperature.getTemperature();
    }

    public WeatherDTO() {

    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
