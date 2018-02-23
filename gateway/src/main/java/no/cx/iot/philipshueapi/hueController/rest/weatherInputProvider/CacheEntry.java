package no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider;

import lombok.Data;

@Data
class CacheEntry {
    private final String place;
    private final String time;
    private final String temperature;
}
