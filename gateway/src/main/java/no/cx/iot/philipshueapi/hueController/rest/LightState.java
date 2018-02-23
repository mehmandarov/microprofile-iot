package no.cx.iot.philipshueapi.hueController.rest;

import lombok.Data;

import java.awt.*;

@Data
public class LightState {
    private final Brightness brightness;
    private final Color hue;
}
