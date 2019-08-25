package no.cx.iot.gateway.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;

import no.cx.iot.gateway.lights.LightState;
import no.cx.iot.gateway.lights.LightStateController;

@Path("/")
public class GatewayEndpoints {

    @Inject
    private LightStateController lightStateController;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(absolute = true, description = "Number of requests to change the lights")
    public List<LightState> switchState() {
        if (!lightStateController.canConnectToFacade()) {
            throw new RuntimeException("Could not connect to facade");
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
