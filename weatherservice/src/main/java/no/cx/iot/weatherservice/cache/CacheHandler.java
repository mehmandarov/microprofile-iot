package no.cx.iot.weatherservice.cache;

import java.util.Optional;

import no.cx.iot.weatherservice.weather.Temperature;

public interface CacheHandler {
    Optional<Temperature> get(String location);

    void updateCache(String location, Temperature temperature);
}
