package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import no.cx.iot.philipshueapi.hueController.rest.Controller;
import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;
import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.YrRestConnector;

@Path("/")
public class RootResource {

    @Inject
    @SuppressWarnings("unused")
    private Controller controller;

    @Inject
    @SuppressWarnings("unused")
    private YrRestConnector yrInputProvider;

    @Inject
    private TimeRestConnector timeInputProvider;

    @SuppressWarnings("unused")
    @PostConstruct
    public void registerInputProviders() {
        controller.registerInputProvider(yrInputProvider);
        controller.registerInputProvider(timeInputProvider);
    }

    @GET
    @Produces("text/plain")
    public Response switchState() {
        return Response.ok(controller.switchStateOfLights()).build();
    }
}
