package no.cx.iot.gateway.weather;

import java.awt.Color;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.gateway.Converter;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.lights.Brightness;
import no.cx.iot.gateway.lights.LightState;

public class WeatherToLightStateConverter implements Converter<Weather> {

    @Inject
    @ConfigProperty(name = "r", defaultValue = "0")
    private int r;

    @Inject
    @ConfigProperty(name = "g", defaultValue = "0")
    private int g;

    @Inject
    @ConfigProperty(name = "b", defaultValue = "0")
    private int b;

    @Inject
    private Logger logger;

    @Override
    public LightState convert(int lightIndex, Weather weather) {
        int hue = new Color(r, g, b).getRGB();
        int brightnessStrength = getBrightnessStrength(weather);
        logger.warning("Converted " + weather + " to " + brightnessStrength);
        return new LightState(lightIndex, InputSource.WEATHER, new Brightness(brightnessStrength), hue);
    }

    private int getBrightnessStrength(Weather weather) {
        int maxBrightness = Brightness.maxBrightness;
        int temperature = weather.getTemperatureInt();
        int adjustment = getAdjustment(temperature);
        return maxBrightness - adjustment;
    }

    private int getAdjustment(int temperature) {
        return (Math.abs(temperature) * 30) + (new Random().nextInt(10));
    }
}
