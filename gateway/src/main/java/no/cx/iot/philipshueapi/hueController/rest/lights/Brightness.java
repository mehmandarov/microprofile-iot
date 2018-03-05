package no.cx.iot.philipshueapi.hueController.rest.lights;

import java.util.logging.Logger;

import javax.inject.Inject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Brightness {

    @Getter
    private final int brightness;

    @Inject
    private Logger logger;

    public Brightness(int brightness) {
        int maxColour = 254;
        this.brightness = Math.max(0, Math.min(brightness, maxColour));
        if (brightness < 0 || brightness > maxColour) {
            logger.info("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }
}
