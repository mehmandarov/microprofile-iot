package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;
import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.WeatherRestConnector;

@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {

    @Inject
    private TimeRestConnector timeRestConnector;

    @Inject
    private WeatherRestConnector weatherRestConnector;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("general")
                .withData("Yr (weather)", weatherRestConnector.canConnect())
                .withData("Clock", timeRestConnector.canConnect())
                .up()
                .build();
    }
}
