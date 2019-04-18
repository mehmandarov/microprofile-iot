package no.cx.iot.weatherservice;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import no.cx.iot.weatherservice.weather.InputProvider;
import no.cx.iot.weatherservice.weather.Temperature;
import no.cx.iot.weatherservice.weather.WeatherDTO;

@Path("/")
public class Endpoint {

    @Inject
    private InputProvider inputProvider;

    @Inject
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the current temperature")
    @APIResponse(name = "WeatherDTO")
    public WeatherDTO getWeather() {
        Temperature temperature = inputProvider.getTemperature();
        logger.info("Got temperature " + temperature + " from the weather service");
        return new WeatherDTO(temperature);
    }
}