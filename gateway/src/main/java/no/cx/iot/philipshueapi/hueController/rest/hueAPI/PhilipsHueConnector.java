package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import no.cx.iot.philipshueapi.hueController.rest.LightState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class PhilipsHueConnector {

    @Inject
    private HttpConnector httpConnector;

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    public int getAllLights() throws IOException {
        String responseText = getResponseText("lights");
        return Integer.valueOf(responseText);
    }

    public String switchStateOfLight(int lightIndex, LightState newLightState) throws IOException {
        String path = "light/" + lightIndex + "/brightness/" + newLightState.getBrightness();
        return getResponseText(path);
    }

    private String getResponseText(String path) throws IOException {
        String responsetext = httpConnector.executeHTTPGetOnHue(path);
        logger.fine("Responsetext: " + responsetext);
        return responsetext;
    }
}
