package no.iot.weatherservice.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import no.iot.weatherservice.weather.yr.YrInputProvider;

@Path("/")
public class WeatherServiceEndpoint {

    @Inject
    private YrInputProvider yrInputProvider;

    @GET
    @Produces("text/plain")
    @Operation(summary = "Get the current temperature")
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
