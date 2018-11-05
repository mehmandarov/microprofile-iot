package no.cx.iot.gateway.lights;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.annotation.Timed;

import no.cx.iot.gateway.facade.FacadeConnector;
import no.cx.iot.gateway.infrastructure.ExceptionWrapper;

@ApplicationScoped
public class LightStateController {

    @Inject
    private FacadeConnector connector;

    @Inject
    private ProposedLightStatesFinder proposedLightStatesFinder;

    @Inject
    private Logger logger;

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
        return ExceptionWrapper.wrapExceptions(connector::getNumberOfLights);
    }

    @Timed(name = "switchState", absolute = true, description = "Time needed to switch state")
    private String switchStateOfLight(int lightIndex) {
        try {
            return Optional.of(lightIndex)
                    .map(proposedLightStatesFinder::getNewStateForLight)
                    .map(lightState -> ExceptionWrapper.wrapExceptions(() -> connector.switchStateOfLight(lightState)))
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
