package no.cx.iot.gateway.facade;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import no.cx.iot.gateway.infrastructure.Connector;
import no.cx.iot.gateway.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class FacadeConnector implements Connector {

    @Inject
    @SuppressWarnings("unused")
    private FacadeURL facadeURL;

    @Inject
    @RestClient
    private FacadeEndpoint facadeEndpoint;

    public int getNumberOfLights() {
        return facadeEndpoint.getNumberOfLights();
    }

    public LightState switchStateOfLight(LightState newLightState) {
        return facadeEndpoint.switchStateOfLight(
                newLightState.getLightIndex(),
                newLightState.getBrightnessInt(),
                facadeURL.getColor(newLightState)
        );
    }

    @Override
    public void testConnection() throws IOException {
        getNumberOfLights();
    }
}
