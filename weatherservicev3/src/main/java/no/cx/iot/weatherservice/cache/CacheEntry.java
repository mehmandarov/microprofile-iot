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

    public TimeDTO getTime() {
        return time;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    CacheEntry(String location, LocalDateTime now, Temperature temperature) {
        this(location, new TimeDTO(now), temperature);
    }

    public CacheEntry() {
    }

    private CacheEntry(String place, TimeDTO time, Temperature temperature) {
        this.place = place;
        this.time = time;
        this.temperature = temperature;
    }

    LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(time.getTimeRepresentation());
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(TimeDTO time) {
        this.time = time;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "CacheEntry{" +
                "place='" + place + '\'' +
                ", time=" + time +
                ", temperature=" + temperature +
                '}';
    }
}