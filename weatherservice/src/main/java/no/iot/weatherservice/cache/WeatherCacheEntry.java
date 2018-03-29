package no.iot.weatherservice.cache;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import no.iot.weatherservice.weather.Temperature;

@AllArgsConstructor
@Getter
class WeatherCacheEntry {
    private final String place;
    private final LocalDateTime time;
    private final Temperature temperature;
}