package no.cx.iot.gateway.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/verify")
public class VerifyResource {

    @GET
    @Produces("text/plain")
    public String doGet() {
        return "Hello from OpenLiberty!";
    }
}
