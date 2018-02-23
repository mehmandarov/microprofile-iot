package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

import no.cx.iot.philipshueapi.hueController.rest.Controller;
import no.cx.iot.philipshueapi.hueController.rest.clockInputProvider.ClockInputProvider;
import no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider.YrInputProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RootResource {

    @Inject
    @SuppressWarnings("unused")
    private Controller controller;

    @Inject
    @SuppressWarnings("unused")
    private ClockInputProvider clockInputProvider;

    @Inject
    @SuppressWarnings("unused")
    private YrInputProvider yrInputProvider;

    @PostConstruct
    public void registerInputProviders() {
        controller.registerInputProvider(clockInputProvider);
        controller.registerInputProvider(yrInputProvider);
    }

    @GET
    @Produces("text/plain")
    public Response switchState() {
        return Response.ok(controller.switchStateOfLights()).build();
    }
}
