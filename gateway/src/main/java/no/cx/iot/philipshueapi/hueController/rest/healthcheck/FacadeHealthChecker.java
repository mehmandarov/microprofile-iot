package no.cx.iot.philipshueapi.hueController.rest.healthcheck;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import no.cx.iot.philipshueapi.hueController.rest.hueAPI.PhilipsHueConnector;

@ApplicationScoped
@Health
public class FacadeHealthChecker implements HealthCheck {

    @Inject
    private PhilipsHueConnector hueConnector;

    @Override
    public HealthCheckResponse call() {
        try {
            hueConnector.getNumberOfLights();
            return HealthCheckResponse.named("Lights-facade")
                    .up()
                    .build();
        }
        catch (Exception e) {
            return HealthCheckResponse.named("Lights-facade")
                    .withData("Lights", e.getMessage())
                    .down()
                    .build();
        }
    }
}
