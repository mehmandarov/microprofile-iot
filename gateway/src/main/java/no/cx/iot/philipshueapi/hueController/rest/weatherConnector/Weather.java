package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Weather {
    private String temperature;

    int getTemperatureInt() {
        return (int) Double.parseDouble(temperature);
    }
}
