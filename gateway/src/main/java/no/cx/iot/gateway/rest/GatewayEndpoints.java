package no.cx.iot.gateway.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.metrics.annotation.Counted;

import no.cx.iot.gateway.lights.LightStateController;

@Path("/")
public class GatewayEndpoints {

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

    @GET
    @Path("/resetlights")
    @Counted(name = "reset", description = "Times lights are reset")
    public String reset() {
        if (!lightStateController.canConnectToFacade()) {
            return "Could not connect to facade";
        }
        lightStateController.reset();
        return "Successfully reset lights";
    }

    @GET
    @Path("lights")
    public String getNumberOfLights() {
        if (!lightStateController.canConnectToFacade()) {
            return "Could not connect to facade";
        }
        return String.valueOf(lightStateController.getNumberOfLights());
    }

    @GET
    @Path("verify")
    @Produces("text/plain")
    public String doGet() {
        return "Hello from OpenLiberty!";
    }
}
