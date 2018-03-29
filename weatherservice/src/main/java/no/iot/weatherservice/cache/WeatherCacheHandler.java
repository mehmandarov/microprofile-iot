package no.iot.weatherservice.cache;

import java.util.Optional;

import no.iot.weatherservice.Temperature;

public interface WeatherCacheHandler {
    Optional<Temperature> get(String currentLocation);

    void updateCache(String currentLocation, Temperature temperature);
}