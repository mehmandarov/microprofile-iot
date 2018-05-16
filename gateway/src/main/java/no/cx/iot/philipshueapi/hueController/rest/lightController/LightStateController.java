package no.cx.iot.philipshueapi.hueController.rest.lightController;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.PhilipsHueConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
@SuppressWarnings("unused")
public class LightStateController {

    @Inject
    private PhilipsHueConnector connector;

    @Inject
    private ProposedLightStatesFinder proposedLightStatesFinder;

    @Inject
    private Logger logger;

    public void registerInputProvider(InputProvider inputProvider) {
        proposedLightStatesFinder.addInputProvider(inputProvider);
    }

    public String switchStateOfLights() {
        return IntStream.range(0, getAllLights())
                .mapToObj(this::switchStateOfLight)
                .peek(light -> logger.info("State of this light: " + light))
                .collect(Collectors.joining("\n"));
    }

    public boolean canConnectToFacade() {
        return connector.canConnect();
    }

    private Integer getAllLights() {
        return wrapExceptions(connector::getNumberOfLights);
    }

    private String switchStateOfLight(int lightIndex) {
        try {
            return Optional.ofNullable(proposedLightStatesFinder.getNewStateForLight((lightIndex)))
                    .map(lightState -> wrapExceptions(() -> connector.switchStateOfLight(lightState)))
                    .map(LightState::toString)
                    .orElse(null);
        }
        catch (Exception e) {
            return getErrorMessage(lightIndex, e);
        }
    }

    private String getErrorMessage(int lightIndex, Exception e) {
        return Optional.ofNullable(e.getMessage())
                .filter(message -> message.contains("is not reachable"))
                .map(message -> "Light " + lightIndex + " is not reachable")
                .orElseThrow(() -> new RuntimeException(e));
    }

}