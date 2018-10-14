package no.iot.weatherservice.cache;

import java.util.Optional;

import no.iot.weatherservice.weather.Temperature;

public interface CacheHandler {
    Optional<Temperature> get(String currentLocation);

    void updateCache(String currentLocation, Temperature temperature);
}
