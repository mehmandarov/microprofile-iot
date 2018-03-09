package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import java.time.LocalDateTime;

import javax.enterprise.context.Dependent;

import no.cx.iot.philipshueapi.hueController.rest.Converter;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@Dependent
public class TimeToLightStateConverter implements Converter<LocalDateTime> {

    @Override
    public LightState convert(LocalDateTime localDateTime) {
        int newBrightness = localDateTime.getNano() % 255; // TODO: Yes, a bit nonsensical and magical
        return new LightState(InputSource.TIME, new Brightness(newBrightness), null);
    }
}
