package no.cx.iot.gateway.facade;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.gateway.lights.LightState;

@ApplicationScoped
public class FacadeURL {

    @Inject
    @ConfigProperty(name="facadehost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name="port", defaultValue = "8083")
    private String port;

    String getFullURL() {
        return "http://" + host + ":" + port +"/hue/";
    }

    String composePath(LightState newLightState) {
        return String.format("light/%s/brightness/%s/color/%s",
                newLightState.getLightIndex(),
                newLightState.getBrightnessInt(),
                getColor(newLightState));
    }

    private Integer getColor(LightState newLightState) {
        return Optional.ofNullable(newLightState.getHueInt()).orElse(0);
    }
}
