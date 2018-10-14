package no.cx.iot.philipshueapi.hueAPI.lightstate;

import java.util.logging.Logger;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import no.cx.iot.philipshueapi.hueAPI.LoggerProvider;

@EqualsAndHashCode(exclude = "logger")
@NoArgsConstructor
public class Brightness {

    @Getter
    private int brightness;

    private Logger logger;

    private static final int maxBrightness = 254;

    public static Brightness getMaxBrightness() {
        return new Brightness(maxBrightness);
    }

    public Brightness(int brightness) {
        logger = LoggerProvider.getLogger(this.getClass());
        setBrightness(brightness);
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }

    void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(brightness, maxBrightness));
        if (brightness < 0 || brightness > maxBrightness) {
            logger.info("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }
}
