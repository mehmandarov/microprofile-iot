package no.cx.iot.gateway.time;

import java.awt.Color;
import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import no.cx.iot.gateway.Converter;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.Printer;
import no.cx.iot.gateway.lights.Brightness;
import no.cx.iot.gateway.lights.LightState;

@Dependent
public class TimeToLightStateConverter implements Converter<ZonedDateTime> {

    @Inject
    Printer printer;

    @Override
    public LightState convert(int lightIndex, ZonedDateTime zonedDateTime) {
        int newBrightness = zonedDateTime.getNano() % 255; // TODO: Yes, a bit nonsensical and magical
        Integer hueInt = Color.blue.getRGB();
        LightState lightState = new LightState(lightIndex, InputSource.TIME, new Brightness(newBrightness), hueInt);
        printer.println("Converted " + zonedDateTime + " to " + lightState);
        return lightState;
    }
}
