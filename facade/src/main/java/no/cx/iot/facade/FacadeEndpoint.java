package no.cx.iot.facade;


import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.opentracing.Traced;

import no.cx.iot.facade.lightstate.LightState;
import no.cx.iot.facade.logic.PhilipsHueController;


@Path("/hue")
@Traced
@ApplicationScoped
public class FacadeEndpoint {

	@Inject
	private PhilipsHueController philipsHueController;

	@Inject
	private Logger logger;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("text/plain")
	@Path("/light/{light}/brightness/{brightness}/color/{color}")
	public LightState switchStateOfLight(@PathParam("light") int lightIndex,
										 @PathParam("brightness") int brightness,
										 @PathParam("color") int color) {
		philipsHueController.setup();
        return philipsHueController.switchStateOfGivenLight(lightIndex, brightness, color);
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lights")
	public Integer getNumberOfLights() {
		philipsHueController.setup();
		return philipsHueController.getNumberOfLights();
	}
}