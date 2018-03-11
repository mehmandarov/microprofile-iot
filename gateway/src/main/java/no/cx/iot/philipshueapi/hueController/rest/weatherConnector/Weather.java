package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import lombok.Data;

@Data
public class Weather {
    private final String temperature;

    int getTemperatureInt() {
        return (int) Double.parseDouble(temperature);
    }
}
