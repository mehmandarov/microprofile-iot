package no.cx.iot.philipshueapi.hueController.rest.healthcheck;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;

@Health
@ApplicationScoped
public class TimeHealthChecker implements HealthCheck {

    @Inject
    private TimeRestConnector timeRestConnector;

    @Override
    public HealthCheckResponse call() {
        try {
            timeRestConnector.canConnect();
            return HealthCheckResponse.named("Time")
                    .up()
                    .build();
        }
        catch (Exception e) {
            return HealthCheckResponse.named("Time")
                    .withData("Time", e.getMessage())
                    .down()
                    .build();
        }
    }
}
