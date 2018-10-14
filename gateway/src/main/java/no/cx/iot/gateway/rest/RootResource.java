package no.cx.iot.gateway.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.metrics.annotation.Counted;

import no.cx.iot.gateway.lights.LightStateController;
import no.cx.iot.gateway.time.TimeRestConnector;
import no.cx.iot.gateway.weather.WeatherRestConnector;

@Path("/")
public class RootResource {

    @Inject
    @SuppressWarnings("unused")
    private LightStateController lightStateController;

    @Inject
    @SuppressWarnings("unused")
    private WeatherRestConnector weatherInputProvider;

    @Inject
    private TimeRestConnector timeInputProvider;

    @SuppressWarnings("unused")
    @PostConstruct
    private void registerInputProviders() {
        lightStateController.registerInputProvider(weatherInputProvider);
        lightStateController.registerInputProvider(timeInputProvider);
    }

    @GET
    @Produces("text/plain")
    @Counted(absolute = true, monotonic = true, description = "Number of requests to change the lights")
    public String switchState() {
        if (!lightStateController.canConnectToFacade()) {
            return "Could not connect to facade";
        }
        return lightStateController.switchStateOfLights();
    }
}
