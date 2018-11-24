package no.cx.iot.facade.lightstate;

public class LightState {
    private final int lightIndex;
    private final InputSource inputSource;
    private final Brightness brightness;
    private Integer hueInt;

    public LightState(int lightIndex, InputSource inputSource, Brightness brightness, Integer hueInt) {
        this.lightIndex = lightIndex;
        this.inputSource = inputSource;
        this.brightness = brightness;
        this.hueInt = hueInt;
    }
}
