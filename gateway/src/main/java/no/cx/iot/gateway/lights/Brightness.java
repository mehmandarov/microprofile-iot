package no.cx.iot.gateway.lights;

import java.io.Serializable;

import no.cx.iot.gateway.infrastructure.Printer;

public class Brightness implements Serializable {

    private int brightness;

    public static final int maxBrightness = 254;

    public int getBrightness() {
        return brightness;
    }

    static Brightness getMaxBrightness() {
        return new Brightness(maxBrightness);
    }

    public Brightness() {
    }

    public Brightness(int brightness) {
        setBrightnessValue(brightness);
    }

    static Brightness of(int brightness) {
        return new Brightness(brightness);
    }

    @Override
    public String toString() {
        return String.valueOf(brightness);
    }

    void setBrightnessValue(int brightness) {
        this.brightness = Math.max(0, Math.min(brightness, maxBrightness));
        if (brightness < 0 || brightness > maxBrightness) {
            new Printer().println("Input brightness was " + brightness +", adjusted to " + this.brightness);
        }
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
