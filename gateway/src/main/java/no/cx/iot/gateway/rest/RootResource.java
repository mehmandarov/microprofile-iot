package no.cx.iot.gateway.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.metrics.annotation.Counted;

import no.cx.iot.gateway.lights.LightStateController;

@Path("/")
public class RootResource {

    @Inject
    private LightStateController lightStateController;

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
