package no.cx.iot.gateway.lights;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;

@ApplicationScoped
class ProposedLightStatesFinder {

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    private Set<InputProvider> inputProviders;

    ProposedLightStatesFinder() {
        inputProviders = new HashSet<>();
    }

    void addInputProvider(InputProvider inputProvider) {
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
