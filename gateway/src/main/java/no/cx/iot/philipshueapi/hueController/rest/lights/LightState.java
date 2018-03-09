package no.cx.iot.philipshueapi.hueController.rest.lights;

import java.awt.Color;

import lombok.Data;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;

@Data
public class LightState {
    private final InputSource inputSource;
    private final Brightness brightness;
    private final Color hue;
}
