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
        String temp = yrInputProvider.getTemperature();
        try {
            Double.parseDouble(temp);
            return Response.ok(yrInputProvider.getTemperature()).build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("We were not able to get the temperature for this location right now.")
                    .build();
        }
    }
}
