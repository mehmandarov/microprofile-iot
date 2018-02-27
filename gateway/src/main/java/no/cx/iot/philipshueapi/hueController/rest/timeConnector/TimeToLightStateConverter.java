package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import no.cx.iot.philipshueapi.hueController.rest.Converter;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import javax.enterprise.context.Dependent;
import java.time.LocalDateTime;

@Dependent
public class TimeToLightStateConverter implements Converter<LocalDateTime> {

    @Override
    public LightState convert(LocalDateTime localDateTime) {
        int newBrightness = localDateTime.getNano() % 255; // TODO: Yes, a bit nonsensical and magical
        return new LightState(new Brightness(newBrightness), null);
    }
}
