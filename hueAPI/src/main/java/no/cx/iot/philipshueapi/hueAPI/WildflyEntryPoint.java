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
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKFacade;


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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("text/plain")
	@Path("/light/{light}/brightness/{brightness}")
	public Response switchStateOfLight(@PathParam("light") int lightIndex, @PathParam("brightness") int brightness) {
		philipsHueController.setup();

		waitUntilBridgeIsSelected();
        return doCall(() ->
                        philipsHueController.switchStateOfGivenLight(sdk.getSelectedBridge(), lightIndex, brightness)
				);
    }

    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("text/plain")
	@Path("/lights")
	public Response getNumberOfLights() {
		philipsHueController.setup();
		waitUntilBridgeIsSelected();

		return doCall(() -> philipsHueController.getNumberOfLights());
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
			if (counter++ > 60) {
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