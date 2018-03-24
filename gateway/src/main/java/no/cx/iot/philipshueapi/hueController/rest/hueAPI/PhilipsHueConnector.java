package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class PhilipsHueConnector {

    @Inject
    private HttpConnector httpConnector;

    public int getNumberOfLights() throws IOException {
        return getResponseText("lights", Integer.class);
    }

    public LightState switchStateOfLight(LightState newLightState) throws IOException {
        String path = "light/" + newLightState.getLightIndex() + "/brightness/" + newLightState.getBrightnessInt() +"/color/" + Optional.ofNullable(newLightState.getHueInt()).orElse(0);
        return getResponseText(path, LightState.class);
    }

    private <T> T getResponseText(String path, Class<T> clazz) throws IOException {
        return httpConnector.executeHTTPGetOnHue(path, clazz);
    }
}
