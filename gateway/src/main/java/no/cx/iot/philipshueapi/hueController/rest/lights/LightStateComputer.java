package no.cx.iot.philipshueapi.hueController.rest.lights;

import java.awt.Color;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.InputSource;

@ApplicationScoped
public class LightStateComputer {

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    public LightState getNewStateForLight(int lightIndex, Set<LightState> proposedLightStates) {
        Brightness newBrightness = getNewBrightness(proposedLightStates);
        Integer newColour = getNewColour(proposedLightStates);

        return new LightState(lightIndex, getUsedSource(proposedLightStates), newBrightness, newColour);
    }

    private InputSource getUsedSource(Set<LightState> proposedLightStates) {
        return proposedLightStates.stream()
                .map(LightState::getInputSource)
                .min(Comparator.comparing(InputSource::getPriority))
                .orElse(null);
    }

    private Brightness getNewBrightness(Set<LightState> proposedLightStates) {
        double newBrightness = proposedLightStates.stream()
                .peek(state -> logger.info(state.toString()))
                .map(LightState::getBrightness)
                .mapToInt(Brightness::getBrightness)
                .peek(brightness -> logger.info(String.valueOf(brightness)))
                .average()
                .orElse(0);
        return new Brightness((int) newBrightness);
    }

    private Integer getNewColour(Set<LightState> proposedLightStates) {
        double average = proposedLightStates.stream()
                .map(LightState::getHue)
                .filter(Objects::nonNull)
                .peek(color -> logger.info("New colour: " +color))
                .mapToInt(Color::getRGB)
                .average()
                .orElse(0);
        return (int) average;
    }
}
