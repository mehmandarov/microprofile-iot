package no.cx.iot.philipshueapi.hueController.rest.healthcheck;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;

@Health
@ApplicationScoped
public class TimeHealthChecker implements HealthChecker<TimeRestConnector>, HealthCheck {

    @Inject
    @Getter
    private TimeRestConnector connector;

    @Override
    public String getName() {
        return "Time";
    }
}
