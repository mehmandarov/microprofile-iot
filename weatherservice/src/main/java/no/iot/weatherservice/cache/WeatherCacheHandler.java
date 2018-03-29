package no.iot.weatherservice.cache;

import java.util.Optional;

public interface WeatherCacheHandler {
    Optional<String> get(String currentLocation);

    void updateCache(String currentLocation, String temperature);
}
