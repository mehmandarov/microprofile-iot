package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class PhilipsHueConnector {

    @Inject
    private HttpConnector httpConnector;

    @Inject
    private Logger logger;

    public int getAllLights() throws IOException {
        return getResponseText("lights", Integer.class);
    }

    public String switchStateOfLight(int lightIndex, LightState newLightState) throws IOException {
        String path = "light/" + lightIndex + "/brightness/" + newLightState.getBrightnessInt();
        return getResponseText(path, String.class);
    }

    private <T> T getResponseText(String path, Class<T> clazz) throws IOException {
        logger.warning("Trying to invoke " + path + " with clazz " + clazz.getName());
        T responsetext = httpConnector.executeHTTPGetOnHue(path, clazz);
        logger.fine("Responsetext: " + responsetext);
        return responsetext;
    }
}
