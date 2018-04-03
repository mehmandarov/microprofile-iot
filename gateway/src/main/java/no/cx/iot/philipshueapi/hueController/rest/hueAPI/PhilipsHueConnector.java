package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.infrastructure.Connector;
import no.cx.iot.philipshueapi.hueController.rest.infrastructure.HttpConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class PhilipsHueConnector implements Connector {

    @Inject
    @SuppressWarnings("unused")
    private HueURL hueURL;

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
        return httpConnector.executeHTTPGet(hueURL.getFullURL() + path, clazz);
    }

    @Override
    public void testConnection() throws IOException {
        getNumberOfLights();
    }
}
