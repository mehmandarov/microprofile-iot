package no.cx.iot.philipshueapi.hueController.rest.lights;

import java.util.logging.Logger;

import javax.inject.Inject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class Brightness {

    @Getter
    private int brightness;

    @Inject
    private Logger logger;

    public Brightness(int brightness) {
        setBrightness(brightness);
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }

    void setBrightness(int brightness) {
        int maxColour = 254;
        this.brightness = Math.max(0, Math.min(brightness, maxColour));
        if (brightness < 0 || brightness > maxColour) {
            logger.info("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }
}
