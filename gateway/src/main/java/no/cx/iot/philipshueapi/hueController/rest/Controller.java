package no.cx.iot.philipshueapi.hueController.rest;

import no.cx.iot.philipshueapi.hueController.rest.hueAPI.PhilipsHueConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightStateComputer;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
public class Controller {

    @SuppressWarnings("unused")
    @Inject
    private PhilipsHueConnector connector;

    private Set<InputProvider> inputProviders;

    @SuppressWarnings("unused")
    @Inject
    private LightStateComputer lightStateComputer;

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    public Controller() {
        inputProviders = new HashSet<>();
    }

    public void registerInputProvider(InputProvider inputProvider) {
        inputProviders.add(inputProvider);
    }

    public String switchStateOfLights() {
        return IntStream.range(0, getAllLights())
                .mapToObj(this::switchStateOfLight)
                .peek(x -> logger.info("State of this light: " + x))
                .collect(Collectors.joining("\n"));
    }

    private int getAllLights() {
        return wrapExceptions(connector::getAllLights);
    }

    private String switchStateOfLight(int lightIndex) {
        return wrapExceptions(() -> connector.switchStateOfLight(lightIndex, getNewStateForLight(lightIndex)));
    }

    private LightState getNewStateForLight(int lightIndex) {
        Set<LightState> proposedLightStates = getProposedLightStates(lightIndex);
        return lightStateComputer.getNewStateForLight(proposedLightStates);
    }

    private Set<LightState> getProposedLightStates(int lightIndex) {
        return inputProviders.stream()
                    .peek(inputProvider -> logger.info(inputProvider.toString()))
                    .map(inputProvider -> inputProvider.getNewStateForLight(lightIndex))
                    .peek(state -> logger.info("Inputprovider suggested " + state + " for light " + lightIndex))
                    .collect(Collectors.toSet());
    }
}
