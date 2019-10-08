package no.cx.iot.gateway;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthChecker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        String classname = LivenessHealthChecker.class.getSimpleName();
        if (canConnect()) {
            return HealthCheckResponse.named(classname)
                    .up()
                    .build();
        }
        else {
            return HealthCheckResponse.named(classname)
                    .withData(classname, "Gateway not alive")
                    .down()
                    .build();
        }
    }

    private boolean canConnect() {
        return new Random().nextBoolean();
    }
}
