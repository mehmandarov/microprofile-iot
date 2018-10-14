package no.cx.iot.philipshueapi.hueController.rest.weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

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
        assertThat(lightState.getInputSource(), is(InputSource.WEATHER));
        assertThat(lightState.getBrightnessInt() >= 200, is(true));
        assertThat(lightState.getHue(), is(notNullValue()));
    }

}