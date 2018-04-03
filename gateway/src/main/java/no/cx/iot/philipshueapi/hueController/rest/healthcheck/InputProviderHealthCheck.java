package no.cx.iot.philipshueapi.hueController.rest.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import no.cx.iot.philipshueapi.hueController.rest.InputProvider;

public interface InputProviderHealthCheck<T extends InputProvider> extends HealthCheck {

    @Override
    default HealthCheckResponse call() {
        if (canConnect()) {
            return HealthCheckResponse.named(getName())
                    .up()
                    .build();
        }
        else {
            return HealthCheckResponse.named(getName())
                    .withData(getName(), "Could not contact " + getName().toLowerCase() + " service")
                    .down()
                    .build();
        }
    }

    String getName();

    T getConnector();

    default boolean canConnect() {
        return getConnector().canConnect();
    }
}
