package no.iot.weatherservice;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import no.iot.weatherservice.weather.LocalWeatherSupplier;
import no.iot.weatherservice.weather.WeatherDTO;

@Path("/")
public class WeatherServiceEndpoint {

    @Inject
    private LocalWeatherSupplier localWeatherSupplier;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the current temperature")
    @APIResponse(name = "WeatherDTO")
    public WeatherDTO getWeather() {
        return localWeatherSupplier.get();
    }
}