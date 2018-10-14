package no.cx.iot.philipshueapi.hueController.rest.facade;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.infrastructure.Connector;
import no.cx.iot.philipshueapi.hueController.rest.infrastructure.HttpConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class FacadeConnector implements Connector {

    @Inject
    @SuppressWarnings("unused")
    private FacadeURL facadeURL;

    @Inject
    private HttpConnector httpConnector;

    @Inject
    private FacadePathComposer facadePathComposer;

    public int getNumberOfLights() throws IOException {
        return getResponseText("lights", Integer.class);
    }

    public LightState switchStateOfLight(LightState newLightState) throws IOException {
        return getResponseText(facadePathComposer.composePath(newLightState), LightState.class);
    }

    private <T> T getResponseText(String path, Class<T> clazz) throws IOException {
        return httpConnector.executeHTTPGet(facadeURL.getFullURL() + path, clazz);
    }

    @Override
    public void testConnection() throws IOException {
        getNumberOfLights();
    }
}
