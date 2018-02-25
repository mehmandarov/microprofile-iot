package no.cx.iot.philipshueapi.hueController.rest.lights;

import lombok.Data;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;

import java.awt.*;

@Data
public class LightState {
    private final Brightness brightness;
    private final Color hue;
}
