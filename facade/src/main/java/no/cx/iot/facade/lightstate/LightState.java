package no.cx.iot.facade.lightstate;

import java.io.Serializable;

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

    public int getLightIndex() {
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
}
