package no.cx.iot.gateway.lights;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Fallback;

import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.Printer;
import no.cx.iot.gateway.time.TimeRestConnector;
import no.cx.iot.gateway.weather.WeatherRestConnector;

@ApplicationScoped
public class ProposedLightStatesFinder {

    @Inject
    private WeatherRestConnector weatherInputProvider;

    @Inject
    private TimeRestConnector timeInputProvider;

    @Inject
    Printer printer;

    private Set<InputProvider> inputProviders;

    ProposedLightStatesFinder() {
        inputProviders = new HashSet<>();
    }

    @PostConstruct
    void registerInputProviders() {
        inputProviders.add(weatherInputProvider);
        inputProviders.add(timeInputProvider);
    }

    @Fallback(fallbackMethod = "getDefaultLightState")
    public LightState getNewStateForLight(int lightIndex) {
        Optional<LightState> lightState = inputProviders.stream()
                .sorted((a, b) -> Integer.compare(b.getPriority(), a.getPriority()))
                .filter(InputProvider::canConnect)
                .map(inputProvider -> inputProvider.getNewStateForLight(lightIndex))
                .filter(Objects::nonNull)
                .limit(1)
                .findAny();
        printer.println(lightState);
        return lightState
                .orElseThrow(() -> new RuntimeException("Could not get new state for light, returning default"));
    }

    public LightState getDefaultLightState(int lightIndex) {
        printer.println("Changing to default light state for light " + lightIndex);
        return new LightState(lightIndex, InputSource.COMPUTED, Brightness.getMaxBrightness(), null);
    }
}
