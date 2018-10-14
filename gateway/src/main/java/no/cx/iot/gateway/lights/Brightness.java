package no.cx.iot.gateway.lights;

import java.util.logging.Logger;

import javax.inject.Inject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import no.cx.iot.gateway.infrastructure.LoggerProvider;

@EqualsAndHashCode(exclude = "logger")
@NoArgsConstructor
public class Brightness {

    @Getter
    private int brightness;

    @Inject
    private Logger logger;

    public static final int maxBrightness = 254;

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
