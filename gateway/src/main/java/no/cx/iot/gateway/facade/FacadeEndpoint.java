package no.cx.iot.gateway.facade;


import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import no.cx.iot.gateway.lights.LightState;

@Path("/hue")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
@Dependent
public interface FacadeEndpoint {

    @GET
    @Consumes("text/plain")
    @Path("/light/{light}/brightness/{brightness}/color/{color}")
    LightState switchStateOfLight(@PathParam("light") int lightIndex,
                                  @PathParam("brightness") int brightness,
                                  @PathParam("color") int color);

    @GET
    @Path("/lights")
    Integer getNumberOfLights();

    @GET
    @Path("/verify")
    String verify();
}