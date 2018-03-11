package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WeatherTest {

    @Test
    public void castsProperlyNegativeDecimal() {
        assertThat(new Weather("-1.2").getTemperatureInt(), is(-1));
    }

    @Test
    public void castsProperlyPositiveDecimal() {
        assertThat(new Weather("2.4").getTemperatureInt(), is(2));
    }

    @Test
    public void nullIsNull() {
        assertThat(new Weather("-0").getTemperatureInt(), is(0));
    }

}