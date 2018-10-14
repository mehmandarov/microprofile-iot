package no.iot.weatherservice.weather;

@FunctionalInterface
public interface InputProvider {
    Temperature getTemperature();
}
