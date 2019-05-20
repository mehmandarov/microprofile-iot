package no.cx.iot.gateway.weather;

public class Weather {
    private String temperature;

    int getTemperatureInt() {
        System.out.println(temperature);
        return (int) Double.parseDouble(temperature);
    }

    @Override
    public String toString() {
        return temperature;
    }


    public Weather(String temperature) {
        this.temperature = temperature;
    }

    public Weather() {

    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
