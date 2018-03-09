package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import no.cx.iot.philipshueapi.hueController.rest.Converter;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public class WeatherToLightStateConverter implements Converter<Weather> {

    @Override
    public LightState convert(Weather temperature) {
        return new LightState(InputSource.WEATHER, new Brightness(getBrightnessStrength(temperature)), null);
    }

    private int getBrightnessStrength(Weather temperature) {
        return 254 - Math.abs(temperature.getTemperatureInt());
    }
}
