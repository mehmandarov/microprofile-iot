package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import no.cx.iot.philipshueapi.hueController.rest.LightStateController;
import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;
import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.WeatherRestConnector;

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
    public Response switchState() {
        if (!lightStateController.canConnectToFacade()) {
            return getErrorMessage();
        }
        return Response.ok(lightStateController.switchStateOfLights()).build();
    }

    private Response getErrorMessage() {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity("Could not connect to facade")
                .build();
    }
}
