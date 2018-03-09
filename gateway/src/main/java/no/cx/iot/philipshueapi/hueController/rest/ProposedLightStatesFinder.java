package no.cx.iot.philipshueapi.hueController.rest;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightStateComputer;

@ApplicationScoped
public class ProposedLightStatesFinder {

    @SuppressWarnings("unused")
    @Inject
    private LightStateComputer lightStateComputer;

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
                .sorted(Comparator.comparing(InputProvider::getPriority))
                .map(inputProvider -> inputProvider.getNewStateForLight(lightIndex))
                .filter(Objects::nonNull)
                .limit(1)
                .findAny()
                .orElseGet(getDefaultLightState());
    }

    private Supplier<LightState> getDefaultLightState() {
        return () -> new LightState(InputSource.COMPUTED, Brightness.getMaxBrightness(), null);
    }
}
