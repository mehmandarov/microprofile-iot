package no.cx.iot.gateway.facade;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import no.cx.iot.gateway.lights.LightState;

@ApplicationScoped
class FacadePathComposer {
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
