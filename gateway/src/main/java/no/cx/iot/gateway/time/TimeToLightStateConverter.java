package no.cx.iot.gateway.time;

import java.awt.Color;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import no.cx.iot.gateway.Converter;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.lights.Brightness;
import no.cx.iot.gateway.lights.LightState;

@Dependent
public class TimeToLightStateConverter implements Converter<ZonedDateTime> {

    @Inject
    private Logger logger;

    @Override
    public LightState convert(int lightIndex, ZonedDateTime zonedDateTime) {
        int newBrightness = zonedDateTime.getNano() % 255; // TODO: Yes, a bit nonsensical and magical
        Integer hueInt = Color.blue.getRGB();
        logger.warning("Converted " + zonedDateTime + " to " + newBrightness);
        return new LightState(lightIndex, InputSource.TIME, new Brightness(newBrightness), hueInt);
    }
}
