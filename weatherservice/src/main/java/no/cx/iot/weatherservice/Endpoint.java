package no.cx.iot.weatherservice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.opentracing.Traced;

import no.cx.iot.weatherservice.weather.InputProvider;
import no.cx.iot.weatherservice.weather.WeatherDTO;

@Path("/")
@ApplicationScoped
@Traced
public class Endpoint {

    @Inject
    private InputProvider inputProvider;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the current temperature")
    @APIResponse(name = "WeatherDTO")
    public WeatherDTO getWeather() {
        return new WeatherDTO(inputProvider.getTemperature());
    }
}