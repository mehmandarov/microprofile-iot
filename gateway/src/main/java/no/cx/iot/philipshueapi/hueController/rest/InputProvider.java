package no.cx.iot.philipshueapi.hueController.rest;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public interface InputProvider {
    LightState getNewStateForLight(int lightIndex);
}
