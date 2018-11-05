package no.cx.iot.gateway.weather;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface WeatherEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/weatherservice")
    Weather getWeather();

}
