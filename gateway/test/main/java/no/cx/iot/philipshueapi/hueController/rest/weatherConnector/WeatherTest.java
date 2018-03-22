package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WeatherTest {

    @Test
    public void castsProperlyNegativeDecimal() {
        assertThat(getWeather("-1.2").getTemperatureInt(), is(-1));
    }

    private Weather getWeather(String temperature) {
        Weather weather = new Weather();
        weather.setTemperature(temperature);
        return weather;
    }

    @Test
    public void castsProperlyPositiveDecimal() {
        assertThat(getWeather("2.4").getTemperatureInt(), is(2));
    }

    @Test
    public void nullIsNull() {
        assertThat(getWeather("-0").getTemperatureInt(), is(0));
    }

}