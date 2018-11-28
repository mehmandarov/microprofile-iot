package no.cx.iot.gateway.facade;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import no.cx.iot.gateway.infrastructure.Connector;
import no.cx.iot.gateway.lights.LightState;

@SuppressWarnings("unused")
@ApplicationScoped
public class FacadeConnector implements Connector {

    @Inject
    @ConfigProperty(name="facadehost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name="port", defaultValue = "8083")
    private String port;

    @Inject
    private Logger logger;

    @Inject
    @RestClient
    private FacadeEndpoint facadeEndpoint;

    public int getNumberOfLights() {
        return facadeEndpoint.getNumberOfLights();
    }

    public LightState switchStateOfLight(LightState newLightState) {
        logger.info("Switching state to " + newLightState);
        LightState newState = facadeEndpoint.switchStateOfLight(
                newLightState.getLightIndex(),
                newLightState.getBrightness().getBrightness(),
                getColor(newLightState)
        );
        logger.info("New state: " + newState);
        return newState;
    }

    @Override
    public void testConnection() throws IOException {
        logger.info("Testing connection to facade");
        facadeEndpoint.verify();
    }

    private Integer getColor(LightState newLightState) {
        return Optional.ofNullable(newLightState.getHueInt()).orElse(0);
    }
}
