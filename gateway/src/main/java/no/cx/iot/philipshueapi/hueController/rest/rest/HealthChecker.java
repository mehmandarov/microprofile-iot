package no.cx.iot.philipshueapi.hueController.rest.rest;

import no.cx.iot.philipshueapi.hueController.rest.TimeRestConnector;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {

    @Inject
    private TimeRestConnector timeRestConnector;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("general")
                .withData("Yr (weather)", "TODO: true")
                .withData("Clock", timeRestConnector.canConnect())
                .up()
                .build();
    }
}
