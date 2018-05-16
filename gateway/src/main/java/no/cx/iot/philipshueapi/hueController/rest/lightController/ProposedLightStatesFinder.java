package no.cx.iot.philipshueapi.hueController.rest.lightController;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

@ApplicationScoped
public class ProposedLightStatesFinder {

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    private Set<InputProvider> inputProviders;

    public ProposedLightStatesFinder() {
        inputProviders = new HashSet<>();
    }

    public void addInputProvider(InputProvider inputProvider) {
        inputProviders.add(inputProvider);
    }

    LightState getNewStateForLight(int lightIndex) {
        return inputProviders.stream()
                .sorted((a, b) -> Integer.compare(b.getPriority(), a.getPriority()))
                .filter(InputProvider::canConnect)
                .map(inputProvider -> inputProvider.getNewStateForLight(lightIndex))
                .filter(Objects::nonNull)
                .limit(1)
                .findAny()
                .orElseGet(getDefaultLightState(lightIndex));
    }

    private Supplier<LightState> getDefaultLightState(int lightIndex) {
        return () -> new LightState(lightIndex, InputSource.COMPUTED, Brightness.getMaxBrightness(), null);
    }
}
