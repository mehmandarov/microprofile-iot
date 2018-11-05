package no.cx.iot.gateway.lights;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.time.TimeRestConnector;
import no.cx.iot.gateway.weather.WeatherRestConnector;

@ApplicationScoped
class ProposedLightStatesFinder {

    @Inject
    private Logger logger;

    @Inject
    private WeatherRestConnector weatherInputProvider;

    @Inject
    private TimeRestConnector timeInputProvider;

    private Set<InputProvider> inputProviders;

    ProposedLightStatesFinder() {
        inputProviders = new HashSet<>();
    }

    @PostConstruct
    void registerInputProviders() {
        inputProviders.add(weatherInputProvider);
        inputProviders.add(timeInputProvider);
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
