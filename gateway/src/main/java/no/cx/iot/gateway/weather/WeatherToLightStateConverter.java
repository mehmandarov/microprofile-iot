package no.cx.iot.gateway.weather;

import java.awt.Color;
import java.util.Random;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.gateway.Converter;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.Printer;
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
    Printer printer;

    @Override
    public LightState convert(int lightIndex, Weather weather) {
        printer.println("Converting " + weather);
        int hue = new Color(r, g, b).getRGB();
        Brightness brightness = new Brightness(getBrightnessStrength(weather));
        LightState lightState = new LightState(lightIndex, InputSource.WEATHER, brightness, hue);
        printer.println("Converted " + weather + " to " + lightState);
        return lightState;
    }

    private int getBrightnessStrength(Weather weather) {
        int maxBrightness = Brightness.maxBrightness;
        int temperature = weather.getTemperatureInt();
        int adjustment = getAdjustment(temperature);
        return maxBrightness - adjustment;
    }

    private int getAdjustment(int temperature) {
        int factor = 1;
        return (Math.abs(temperature) * factor) + (new Random().nextInt(200));
    }
}
