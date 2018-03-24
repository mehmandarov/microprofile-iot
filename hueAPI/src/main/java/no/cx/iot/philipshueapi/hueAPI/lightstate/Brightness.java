package no.cx.iot.philipshueapi.hueAPI.lightstate;

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

    private static final int maxBrightess = 254;

    public static Brightness getMaxBrightness() {
        return new Brightness(maxBrightess);
    }

    public Brightness(int brightness) {
        setBrightness(brightness);
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }

    private void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(brightness, maxBrightess));
        if (brightness < 0 || brightness > maxBrightess) {
            logger.info("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }
}
