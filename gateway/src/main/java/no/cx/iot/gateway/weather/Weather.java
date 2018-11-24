package no.cx.iot.gateway.weather;

public class Weather {
    private String temperature;

    int getTemperatureInt() {
        return (int) Double.parseDouble(temperature);
    }
}
