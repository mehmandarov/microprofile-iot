package no.cx.iot.weatherservice.weather;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Temperature {

    private String temperature;

    public Temperature(String temperature) {
        setTemperature(temperature);
    }

    public void setTemperature(String temperature) {
        validate(temperature);
        this.temperature = temperature;
    }

    private void validate(String temperature) {
        Double.parseDouble(temperature);
    }
}
