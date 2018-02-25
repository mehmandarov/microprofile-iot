package no.cx.iot.philipshueapi.hueController.rest;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public interface InputProvider<T> {
    T getDataForLight(int lightIndex);

    LightState getNewStateForLight(int lightIndex);

}
