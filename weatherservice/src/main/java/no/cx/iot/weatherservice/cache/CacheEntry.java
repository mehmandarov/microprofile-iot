package no.cx.iot.weatherservice.cache;

import java.time.LocalDateTime;

import no.cx.iot.weatherservice.weather.Temperature;

class CacheEntry {

    private String place;
    private TimeDTO time;

    private Temperature temperature;


    public String getPlace() {
        return place;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    CacheEntry(String location, LocalDateTime now, Temperature temperature) {
        this(location, new TimeDTO(now), temperature);
    }

    private CacheEntry(String place, TimeDTO time, Temperature temperature) {
        this.place = place;
        this.time = time;
        this.temperature = temperature;
    }

    LocalDateTime getTime() {
        return LocalDateTime.parse(time.getTimeRepresentation());
    }
}