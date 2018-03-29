package no.iot.weatherservice.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherCacheEntry {
    private final String place;
    private final String time;
    private final String temperature;

}