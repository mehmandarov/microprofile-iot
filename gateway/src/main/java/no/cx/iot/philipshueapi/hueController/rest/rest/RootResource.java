package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import no.cx.iot.philipshueapi.hueController.rest.LightStateSwitcher;
import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;
import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.WeatherRestConnector;

@Path("/")
public class RootResource {

    @Inject
    @SuppressWarnings("unused")
    private LightStateSwitcher lightStateSwitcher;

    @Inject
    @SuppressWarnings("unused")
    private WeatherRestConnector yrInputProvider;

    @Inject
    private TimeRestConnector timeInputProvider;

    @SuppressWarnings("unused")
    @PostConstruct
    public void registerInputProviders() {
        lightStateSwitcher.registerInputProvider(yrInputProvider);
        lightStateSwitcher.registerInputProvider(timeInputProvider);
    }

    @GET
    @Produces("text/plain")
    public Response switchState() {
        return Response.ok(lightStateSwitcher.switchStateOfLights()).build();
    }
}
