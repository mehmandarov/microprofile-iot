package no.cx.iot.weatherservice.weather;

@FunctionalInterface
public interface InputProvider {
    Temperature getTemperature();
}
