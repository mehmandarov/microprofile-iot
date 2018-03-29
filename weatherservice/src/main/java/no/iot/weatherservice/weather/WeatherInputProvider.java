package no.iot.weatherservice.weather;

import no.iot.weatherservice.Temperature;

public interface WeatherInputProvider {
    Temperature getTemperature();
}
