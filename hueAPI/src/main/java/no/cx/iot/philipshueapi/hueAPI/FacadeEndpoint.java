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
	public Response switchStateOfLight(@PathParam("light") int lightIndex,
									   @PathParam("brightness") int brightness,
									   @PathParam("color") int color) {
		setupForSDKCall();
        return wrapInResponse(() -> philipsHueController.switchStateOfGivenLight(bridgeSelector.getBridge(), lightIndex, brightness, color));
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lights")
	public Response getNumberOfLights() {
		setupForSDKCall();
		return wrapInResponse(() -> philipsHueController.getNumberOfLights(bridgeSelector.isUseRealBridge()));
	}

	private void setupForSDKCall() {
		philipsHueController.setup();
		bridgeSelector.waitUntilBridgeIsSelected();
	}

	private <T> Response wrapInResponse(Supplier<T> responseTextSupplier) {
        try {
			T typedResponse = responseTextSupplier.get();
			return Response.ok(typedResponse).build();
        }
        catch (HueAPIException e) {
            logger.severe(e.getMessage());
			return Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(e.getMessage())
					.build();
        }
    }
}