package no.iot.weatherservice.cache;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.iot.weatherservice.weather.Temperature;

@AllArgsConstructor
@NoArgsConstructor
@Setter
class WeatherCacheEntry {
    @Getter
    private String place;
    private TimeDTO time;

    @Getter
    private Temperature temperature;

    WeatherCacheEntry(String currentLocation, LocalDateTime now, Temperature temperature) {
        this(currentLocation, new TimeDTO(now), temperature);
    }

    LocalDateTime getTime() {
        return LocalDateTime.parse(time.getTimeRepresentation());
    }
}