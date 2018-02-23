package no.cx.iot.philipshueapi.hueController.rest;

public interface InputProvider {
    LightState getNewStateForLight(int lightIndex);
}
