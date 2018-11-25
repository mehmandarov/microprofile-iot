package no.cx.iot.facade.lightstate;

import java.io.Serializable;
import java.util.logging.Logger;

import no.cx.iot.facade.LoggerProvider;

public class Brightness implements Serializable {

    private int brightness;

    public int getBrightness() {
        return brightness;
    }

    private Logger logger;

    private static final int maxBrightness = 254;

    public static Brightness getMaxBrightness() {
        return new Brightness(maxBrightness);
    }

    public Brightness() {
        logger = LoggerProvider.getLogger(this.getClass());
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
