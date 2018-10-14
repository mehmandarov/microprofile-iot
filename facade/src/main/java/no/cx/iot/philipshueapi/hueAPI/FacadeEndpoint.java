package no.cx.iot.philipshueapi.hueAPI;


import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import no.cx.iot.philipshueapi.hueAPI.bridge.Bridge;
import no.cx.iot.philipshueapi.hueAPI.lightstate.LightState;
import no.cx.iot.philipshueapi.hueAPI.logic.PhilipsHueController;


@Path("/hue")
public class FacadeEndpoint {

	@Inject
	private PhilipsHueController philipsHueController;

	@Inject
	private Logger logger;

	@Inject
	private BridgeSelector bridgeSelector;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("text/plain")
	@Path("/light/{light}/brightness/{brightness}/color/{color}")
	public LightState switchStateOfLight(@PathParam("light") int lightIndex,
										 @PathParam("brightness") int brightness,
										 @PathParam("color") int color) {
		setupForSDKCall();
        return philipsHueController.switchStateOfGivenLight(getBridge(), lightIndex, brightness, color);
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lights")
	public Integer getNumberOfLights() {
		setupForSDKCall();
		return philipsHueController.getNumberOfLights(getBridge());
	}

	private void setupForSDKCall() {
		philipsHueController.setup();
		bridgeSelector.waitUntilBridgeIsSelected();
	}

	private Bridge getBridge() {
		return bridgeSelector.getBridge();
	}
}