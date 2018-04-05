package no.cx.iot.philipshueapi.hueController.rest.healthcheck;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.PhilipsHueConnector;

@ApplicationScoped
@Health
public class FacadeHealthChecker implements HealthChecker<PhilipsHueConnector>, HealthCheck {

    @Inject
    @Getter
    private PhilipsHueConnector connector;

    @Override
    public String getName() {
        return "Facade";
    }

}
