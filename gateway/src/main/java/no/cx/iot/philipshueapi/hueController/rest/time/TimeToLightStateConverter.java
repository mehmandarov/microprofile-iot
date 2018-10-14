package no.cx.iot.philipshueapi.hueController.rest.time;

import java.awt.Color;
import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import no.cx.iot.philipshueapi.hueController.rest.Converter;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@Dependent
public class TimeToLightStateConverter implements Converter<ZonedDateTime> {

    @Override
    public LightState convert(int lightIndex, ZonedDateTime zonedDateTime) {
        int newBrightness = zonedDateTime.getNano() % 255; // TODO: Yes, a bit nonsensical and magical
        Integer hueInt = Color.blue.getRGB();
        return new LightState(lightIndex, InputSource.TIME, new Brightness(newBrightness), hueInt);
    }
}
