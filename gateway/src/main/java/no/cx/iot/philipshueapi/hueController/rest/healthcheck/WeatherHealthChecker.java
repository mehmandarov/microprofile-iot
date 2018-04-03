package no.cx.iot.philipshueapi.hueController.rest.healthcheck;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.WeatherRestConnector;

@Health
@ApplicationScoped
public class WeatherHealthChecker implements InputProviderHealthCheck<WeatherRestConnector> {

    @Inject
    @Getter
    private WeatherRestConnector connector;

    @Override
    public String getName() {
        return "Weather";
    }
}
