package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import java.awt.Color;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.philipshueapi.hueController.rest.Converter;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public class WeatherToLightStateConverter implements Converter<Weather> {

    @Inject
    @ConfigProperty(name = "r", defaultValue = "0")
    private int r;

    @Inject
    @ConfigProperty(name = "g", defaultValue = "0")
    private int g;

    @Inject
    @ConfigProperty(name = "b", defaultValue = "255")
    private int b;

    @Override
    public LightState convert(int lightIndex, Weather temperature) {
        int hue = new Color(r, g, b).getRGB();
        return new LightState(lightIndex, InputSource.WEATHER, new Brightness(getBrightnessStrength(temperature)), hue);
    }

    private int getBrightnessStrength(Weather temperature) {
        return Brightness.maxBrightess - (Math.abs(temperature.getTemperatureInt())*30);
    }
}
