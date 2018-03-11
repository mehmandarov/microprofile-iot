package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import java.awt.Color;

import no.cx.iot.philipshueapi.hueController.rest.Converter;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public class WeatherToLightStateConverter implements Converter<Weather> {

    @Override
    public LightState convert(int lightIndex, Weather temperature) {
        int hue = Color.MAGENTA.getRGB();
        return new LightState(lightIndex, InputSource.WEATHER, new Brightness(getBrightnessStrength(temperature)), hue);
    }

    private int getBrightnessStrength(Weather temperature) {
        return Brightness.maxBrightess - (Math.abs(temperature.getTemperatureInt())*30);
    }
}
