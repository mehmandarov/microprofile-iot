package no.cx.iot.philipshueapi.hueController.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.logging.Logger;

@EqualsAndHashCode
public class Brightness {

    @Getter
    private final int brightness;

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    public Brightness(int brightness) {
        this.brightness = brightness < 0 ? 0 : (brightness > 254 ? 254 : brightness);
        if (brightness < 0 || brightness > 254) {
            logger.info("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }
}
