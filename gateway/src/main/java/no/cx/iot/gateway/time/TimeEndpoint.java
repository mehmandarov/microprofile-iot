package no.cx.iot.gateway.time;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface TimeEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timeservice")
    TimeDTO getCurrentTime();

}
