package no.cx.iot.gateway.lights;

import java.io.Serializable;

import no.cx.iot.gateway.InputSource;

public class LightState implements Serializable {

    private int lightIndex;
    private InputSource inputSource;
    private Brightness brightness;
    private Integer hueInt;

    public LightState() {
    }

    public LightState(int lightIndex, InputSource inputSource, Brightness brightness, Integer hueInt) {
        this.lightIndex = lightIndex;
        this.inputSource = inputSource;
        this.brightness = brightness;
        this.hueInt = hueInt;
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

    public Integer getLightIndex() {
        return lightIndex;
    }

    public InputSource getInputSource() {
        return inputSource;
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public Integer getHueInt() {
        return hueInt;
    }

    public void setLightIndex(int lightIndex) {
        this.lightIndex = lightIndex;
    }

    public void setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    public void setBrightness(Brightness brightness) {
        this.brightness = brightness;
    }

    public void setHueInt(Integer hueInt) {
        this.hueInt = hueInt;
    }
}
