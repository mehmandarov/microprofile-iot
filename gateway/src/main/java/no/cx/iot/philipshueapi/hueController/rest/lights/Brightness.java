package no.cx.iot.philipshueapi.hueController.rest.lights;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.inject.Inject;
import java.util.logging.Logger;

@EqualsAndHashCode
public class Brightness {

    @Getter
    private final int brightness;

    @Inject
    private Logger logger;

    public Brightness(int brightness) {
        int maxColour = 254;
        this.brightness = brightness < 0 ? 0 : (brightness > maxColour ? maxColour : brightness);
        if (brightness < 0 || brightness > maxColour) {
            logger.info("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }
}
