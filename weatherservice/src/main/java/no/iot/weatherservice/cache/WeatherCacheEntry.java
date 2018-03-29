package no.iot.weatherservice.cache;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class WeatherCacheEntry {
    private final String place;
    private final String time;
    private final String temperature;

    LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(time);
    }
}