package no.cx.iot.philipshueapi.hueController.rest.lights;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class LightStateComputer {

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    public LightState getNewStateForLight(Set<LightState> proposedLightStates) {
        Brightness newBrightness = getNewBrightness(proposedLightStates);
        Color newColour = getNewColour(proposedLightStates);

        return new LightState(newBrightness, newColour);
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

    private Color getNewColour(Set<LightState> proposedLightStates) {
        double average = proposedLightStates.stream()
                .map(LightState::getHue)
                .filter(Objects::nonNull)
                .peek(color -> logger.info("New colour: " +color))
                .mapToInt(Color::getRGB)
                .average()
                .orElse(0);
        return new Color((int) average);
    }
}
