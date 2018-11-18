package no.cx.iot.weatherservice.weather.yr;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/place")
public interface YrService {

    @GET
    @Path("/{country}/{cityPart}/forecast.xml")
    String getWeather(
            @PathParam("country") String country,
            @PathParam("cityPart") String cityPart
    );
}
