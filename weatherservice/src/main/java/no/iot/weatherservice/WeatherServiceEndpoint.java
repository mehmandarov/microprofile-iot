package no.iot.weatherservice;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import no.iot.weatherservice.weather.LocalWeatherSupplier;

@Path("/")
public class WeatherServiceEndpoint {

    @Inject
    private LocalWeatherSupplier localWeatherSupplier;

    @Inject
    @ConfigProperty(name = "errorMessage")
    private String errorMessage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the current temperature")
    @APIResponse(name = "WeatherDTO")
    public Response getWeather() {
        try {
            return Response
                    .ok(localWeatherSupplier.get())
                    .build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(errorMessage)
                    .build();
        }
    }
}