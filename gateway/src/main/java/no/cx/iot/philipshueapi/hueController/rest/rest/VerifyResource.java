package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/verify")
public class VerifyResource {

    @GET
    @Produces("text/plain")
    public String doGet() {
        return "Hello from WildFly Swarm!";
    }
}
