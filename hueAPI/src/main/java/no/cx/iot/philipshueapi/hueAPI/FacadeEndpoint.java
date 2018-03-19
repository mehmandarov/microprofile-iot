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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.philipshueapi.hueAPI.logic.Bridge;
import no.cx.iot.philipshueapi.hueAPI.logic.PhilipsHueController;
import no.cx.iot.philipshueapi.hueAPI.logic.SDKBridge;
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKFacade;


@Path("/hue")
@SuppressWarnings("unused")
public class FacadeEndpoint {

	@Inject
	private PhilipsHueController philipsHueController;

	@Inject
	private SDKFacade sdk;

	@Inject
	private Logger logger;

	@Inject
	@ConfigProperty(name = "useRealBridge", defaultValue = "false")
	private boolean useRealBridge;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("text/plain")
	@Path("/light/{light}/brightness/{brightness}/color/{color}")
	public Response switchStateOfLight(@PathParam("light") int lightIndex,
									   @PathParam("brightness") int brightness,
									   @PathParam("color") int color) {
		philipsHueController.setup();
		waitUntilBridgeIsSelected();
        return doCall(() -> philipsHueController.switchStateOfGivenLight(getBridge(), lightIndex, brightness, color));
    }

	private Bridge getBridge() {
		return useRealBridge ? new SDKBridge(sdk.getSelectedBridge()) : new DummyBridge();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("text/plain")
	@Path("/lights")
	public Response getNumberOfLights() {
		philipsHueController.setup();
		waitUntilBridgeIsSelected();

		return doCall(() -> philipsHueController.getNumberOfLights(useRealBridge));
	}

	private void waitUntilBridgeIsSelected() {
	    int counter = 0;
	    if (!useRealBridge) {
	    	return;
		}
		while (sdk.getSelectedBridge() == null) {
			try {
				logger.info("Waiting for bridge selection");
				Thread.sleep(400);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (counter++ > 50) {
				throw new HueAPIException("Waited too long for bridgeselection");
			}
		}
	}

    private <T> Response doCall(Supplier<T> responseTextSupplier) {
        try {
            return Response.ok(responseTextSupplier.get()).build();
        }
        catch (HueAPIException e) {
            logger.severe(e.getMessage());
			return Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(e.getMessage())
					.build();
        }
    }
}