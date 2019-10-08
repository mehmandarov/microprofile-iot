package no.cx.iot.gateway;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessHealthChecker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        String classname = ReadinessHealthChecker.class.getSimpleName();
        if (canConnect()) {
            return HealthCheckResponse.named(classname)
                    .up()
                    .build();
        }
        else {
            return HealthCheckResponse.named(classname)
                    .withData(classname, "Gateway not ready to accept traffic")
                    .down()
                    .build();
        }
    }

    private boolean canConnect() {
        return new Random().nextBoolean();
    }
}
