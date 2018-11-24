package no.cx.iot.gateway.lights;

import java.util.logging.Logger;

import javax.inject.Inject;

import no.cx.iot.gateway.infrastructure.LoggerProvider;

public class Brightness {

    private int brightness;

    @Inject
    private Logger logger;

    public static final int maxBrightness = 254;

    public int getBrightness() {
        return brightness;
    }

    public static Brightness getMaxBrightness() {
        return new Brightness(maxBrightness);
    }

    public Brightness(int brightness) {
        logger = LoggerProvider.getLogger(this.getClass());
        setBrightness(brightness);
    }

    public static Brightness of(int brightness) {
        return new Brightness(brightness);
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
