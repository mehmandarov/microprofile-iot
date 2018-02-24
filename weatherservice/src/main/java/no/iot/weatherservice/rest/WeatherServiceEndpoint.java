package no.iot.weatherservice.rest;

import no.iot.weatherservice.weather.yr.YrInputProvider;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class WeatherServiceEndpoint {

    @Inject
    private YrInputProvider yrInputProvider;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok(yrInputProvider.getTemperature()).build();
    }
}
