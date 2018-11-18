package no.cx.iot.weatherservice.weather.yr;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;

@Path("/place")
public interface YrService {

    @GET
    @Path("/{country}/{cityPart}/forecast.xml")
    @Retry(maxRetries = 2)
    @CircuitBreaker
    String getWeather(
            @PathParam("country") String country,
            @PathParam("cityPart") String cityPart
    );
}
