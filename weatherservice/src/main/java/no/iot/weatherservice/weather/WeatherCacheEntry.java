package no.iot.weatherservice.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherCacheEntry {
    private final String place;
    private final String time;
    private final String temperature;

}