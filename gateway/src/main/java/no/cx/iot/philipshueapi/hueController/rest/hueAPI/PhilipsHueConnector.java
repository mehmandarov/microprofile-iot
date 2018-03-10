package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class PhilipsHueConnector {

    @Inject
    private HttpConnector httpConnector;

    public int getAllLights() throws IOException {
        return getResponseText("lights", Integer.class);
    }

    public LightState switchStateOfLight(int lightIndex, LightState newLightState) throws IOException {
        String path = "light/" + lightIndex + "/brightness/" + newLightState.getBrightnessInt();
        return getResponseText(path, LightState.class);
    }

    private <T> T getResponseText(String path, Class<T> clazz) throws IOException {
        return httpConnector.executeHTTPGetOnHue(path, clazz);
    }
}
