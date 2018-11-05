package no.cx.iot.gateway.weather;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.lights.LightState;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WeatherToLightStateConverterTest {

    @InjectMocks
    private WeatherToLightStateConverter converter;

    @Test
    public void t() {
        Weather weather = new Weather();
        weather.setTemperature("0.0");
        LightState lightState = converter.convert(0, weather);
        assertThat(lightState.getLightIndex(), is(0));
        assertThat(lightState.getInputSource(), CoreMatchers.is(InputSource.WEATHER));
        assertThat(lightState.getBrightnessInt() >= 200, is(true));
        assertThat(lightState.getHue(), is(notNullValue()));
    }

}