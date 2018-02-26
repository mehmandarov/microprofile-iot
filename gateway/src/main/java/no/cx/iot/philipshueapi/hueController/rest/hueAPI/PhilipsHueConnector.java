package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

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
        String path = "light/" + lightIndex + "/brightness/" + newLightState.getBrightness();
        return getResponseText(path, String.class);
    }

    private <T> T getResponseText(String path, Class<T> clazz) throws IOException {
        T responsetext = httpConnector.executeHTTPGetOnHue(path, clazz);
        logger.fine("Responsetext: " + responsetext);
        return responsetext;
    }
}
