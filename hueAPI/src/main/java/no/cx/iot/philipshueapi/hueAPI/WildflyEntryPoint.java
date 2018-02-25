package no.cx.iot.philipshueapi.hueAPI;


import no.cx.iot.philipshueapi.hueAPI.logic.PhilipsHueController;
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.function.Supplier;
import java.util.logging.Logger;


@Path("/hue")
public class WildflyEntryPoint {

	@SuppressWarnings("unused")
	@Inject
	private PhilipsHueController philipsHueController;

	@SuppressWarnings("unused")
	@Inject
	private SDKFacade sdk;

	@Inject
	private Logger logger;

	@GET
	@Produces("text/plain")
	@Consumes("text/plain")
	@Path("/light/{light}/brightness/{brightness}")
	public Response switchStateOfLight(@PathParam("light") int lightIndex, @PathParam("brightness") int brightness) {
		philipsHueController.setup();

		waitUntilBridgeIsSelected();
        return doCall(() ->
                        philipsHueController.switchStateOfGivenLight(sdk.getSelectedBridge(), lightIndex, brightness),
                () -> getResponseText(lightIndex, brightness));
    }

    @GET
	@Produces("text/plain")
	@Consumes("text/plain")
	@Path("/lights")
	public Response getNumberOfLights() {
		philipsHueController.setup();
		waitUntilBridgeIsSelected();

		return doCall(() -> {}, () -> philipsHueController.getNumberOfLights() + "");
	}

	private void waitUntilBridgeIsSelected() {
	    int counter = 0;
		while (sdk.getSelectedBridge() == null) {
			try {
				logger.info("Waiting for bridgeselection");
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (counter++ > 30) {
				throw new HueAPIException("Waited too long for bridgeselection");
			}
		}
	}

	private String getResponseText(int lightIndex, Integer newBrightness) {
		return "The new brightness of light " + lightIndex + " is " + newBrightness;
	}

    private Response doCall(Runnable runnable, Supplier<String> responseTextSupplier) {
        try {
            runnable.run();
            return Response.ok(responseTextSupplier.get()).build();
        }
        catch (HueAPIException e) {
            logger.severe(e.getMessage());
            return Response.ok(e.getMessage()).build();
        }
    }
}