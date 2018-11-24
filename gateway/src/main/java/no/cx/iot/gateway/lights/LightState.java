package no.cx.iot.gateway.lights;

import java.awt.Color;
import java.util.Optional;

import no.cx.iot.gateway.InputSource;

public class LightState {
    private Integer lightIndex;
    private InputSource inputSource;
    private Brightness brightness;
    private Integer hueInt;

    public LightState(Integer lightIndex, InputSource inputSource, Brightness brightness, Integer hueInt) {
        this.lightIndex = lightIndex;
        this.inputSource = inputSource;
        this.brightness = brightness;
        this.hueInt = hueInt;
    }

    public Color getHue() {
        return Optional.ofNullable(hueInt)
                .map(Color::new)
                .orElse(Color.RED);
    }

    public Integer getLightIndex() {
        return lightIndex;
    }

    public int getBrightnessInt() {
        return brightness.getBrightness();
    }

    public Integer getHueInt() {
        return hueInt;
    }

    @Override
    public String toString() {
        return "LightState{" +
                "lightIndex=" + lightIndex +
                ", inputSource=" + inputSource +
                ", brightness=" + brightness +
                ", hueInt=" + hueInt +
                '}';
    }
}
