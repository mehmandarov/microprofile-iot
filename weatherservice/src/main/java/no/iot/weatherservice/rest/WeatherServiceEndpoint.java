package no.iot.weatherservice.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import no.iot.weatherservice.supplier.LocalWeatherSupplier;
import no.iot.weatherservice.weather.yr.YrInputProvider;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/")
public class WeatherServiceEndpoint {

    @Inject
    private YrInputProvider yrInputProvider;

    @Inject
    private LocalWeatherSupplier localWeatherSupplier;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the current temperature")
    @APIResponse(name = "WeatherDTO")
    public Response doGet() {
        String temp = yrInputProvider.getTemperature();
        try {
            Double.parseDouble(temp);
            return Response.ok(localWeatherSupplier.get()).build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("We were not able to get the temperature for this location right now.")
                    .build();
        }
    }
}
