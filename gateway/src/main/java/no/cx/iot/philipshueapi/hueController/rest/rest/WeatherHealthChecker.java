package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.WeatherRestConnector;

@Health
@ApplicationScoped
public class WeatherHealthChecker implements HealthCheck {


    @Inject
    private WeatherRestConnector weatherRestConnector;

    @Override
    public HealthCheckResponse call() {
        try {
            weatherRestConnector.canConnect();
            return HealthCheckResponse.named("Weather")
                    .up()
                    .build();
        }
        catch (Exception e) {
            return HealthCheckResponse.named("Weather")
                    .withData("Weather", e.getMessage())
                    .down()
                    .build();
        }
    }
}
