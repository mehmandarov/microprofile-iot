package no.cx.iot.weatherservice.weather;

public class Temperature {

    private String temperature;

    public Temperature(String temperature) {
        setTemperature(temperature);
    }

    public void setTemperature(String temperature) {
        validate(temperature);
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    private void validate(String temperatureString) {
        double temperature = Double.parseDouble(temperatureString);
        assert temperature >= -273.15;
        assert temperature < 100;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temperature='" + temperature + '\'' +
                '}';
    }
}
