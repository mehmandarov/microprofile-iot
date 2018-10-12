package no.iot.weatherservice.weather;

@FunctionalInterface
public interface WeatherInputProvider {
    Temperature getTemperature();
}
