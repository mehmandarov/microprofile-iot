package no.cx.iot.gateway.weather;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
@Dependent
public interface WeatherEndpoint {

    @GET
    @Path("/weatherservice")
    Weather getWeather();

}
