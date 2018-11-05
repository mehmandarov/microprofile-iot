package no.cx.iot.gateway;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        String classname = HealthChecker.class.getSimpleName();
        if (canConnect()) {
            return HealthCheckResponse.named(classname)
                    .up()
                    .build();
        }
        else {
            return HealthCheckResponse.named(classname)
                    .withData(classname, "Could not contact gateway")
                    .down()
                    .build();
        }
    }

    private boolean canConnect() {
        return new Random().nextBoolean();
    }
}
