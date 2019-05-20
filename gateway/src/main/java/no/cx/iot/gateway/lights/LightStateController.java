package no.cx.iot.gateway.lights;

import java.awt.Color;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.annotation.Timed;

import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.facade.FacadeConnector;
import no.cx.iot.gateway.infrastructure.Printer;

import static no.cx.iot.gateway.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
public class LightStateController {

    @Inject
    private FacadeConnector facade;

    @Inject
    private ProposedLightStatesFinder proposedLightStatesFinder;

    @Inject
    private Printer printer;

    public String switchStateOfLights() {
        printer.println("Switching state of lights");
        return IntStream.range(0, getAllLights())
                .mapToObj(this::switchStateOfLight)
                .peek(light -> printer.println("State of this light: " + light))
                .collect(Collectors.joining("\n"));
    }

    @Timed(absolute = true, name = "canConnect", description = "Can connect-checks to facade")
    public boolean canConnectToFacade() {
        return facade.canConnect();
    }

    private Integer getAllLights() {
        return wrapExceptions(facade::getNumberOfLights);
    }

    @Timed(name = "switchState", absolute = true, description = "Time needed to switch state")
    private String switchStateOfLight(int lightIndex) {
        try {
            return Optional.of(lightIndex)
                    .map(proposedLightStatesFinder::getNewStateForLight)
                    .map(lightState -> wrapExceptions(() -> facade.switchStateOfLight(lightState)))
                    .map(LightState::toString)
                    .orElse(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return getErrorMessage(lightIndex, e);
        }
    }

    private String getErrorMessage(int lightIndex, Exception e) {
        return Optional.ofNullable(e.getMessage())
                .filter(message -> message.contains("is not reachable"))
                .map(message -> "Light " + lightIndex + " is not reachable")
                .orElseThrow(() -> new RuntimeException(e));
    }

    public void reset() {
        IntStream.range(0, facade.getNumberOfLights())
                .forEach(index -> facade.switchStateOfLight(
                        new LightState(index, InputSource.COMPUTED, Brightness.of(0), Color.white.getRGB())
                        )
                );
    }
}
