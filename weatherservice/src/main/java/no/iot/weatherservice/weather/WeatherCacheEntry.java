package no.iot.weatherservice.weather;

public class WeatherCacheEntry {
    private String place;
    private String time;
    private String temperature;

    public WeatherCacheEntry(String place, String time, String temperature) {
        this.place = place;
        this.time = time;
        this.temperature = temperature;
    }

    public WeatherCacheEntry() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
