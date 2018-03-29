package no.iot.weatherservice;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Temperature {
    private final String temperature;

    public Temperature(String temperature) {
        validate(temperature);
        this.temperature = temperature;
    }

    private void validate(String temperature) {
        Double.parseDouble(temperature);
    }
}
