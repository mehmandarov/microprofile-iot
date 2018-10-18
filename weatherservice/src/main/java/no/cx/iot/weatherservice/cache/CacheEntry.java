package no.cx.iot.weatherservice.cache;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.cx.iot.weatherservice.weather.Temperature;

@AllArgsConstructor
@NoArgsConstructor
@Setter
class CacheEntry {
    @Getter
    private String place;
    private TimeDTO time;

    @Getter
    private Temperature temperature;

    CacheEntry(String location, LocalDateTime now, Temperature temperature) {
        this(location, new TimeDTO(now), temperature);
    }

    LocalDateTime getTime() {
        return LocalDateTime.parse(time.getTimeRepresentation());
    }
}