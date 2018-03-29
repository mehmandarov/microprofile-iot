package no.iot.timeservice;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import no.iot.timeservice.supplier.ZonedDateTimeNowSupplier;


@Path("/")
public class TimeServiceEndpoint {

    @SuppressWarnings("unused")
    @Inject
    private ZonedDateTimeNowSupplier dateTimeNowSupplier;

    @Inject
    private Logger logger;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets the current time", description = "The format is a wrapped ZonedDateTime")
    public Response doGet() {
        try {
            logger.info("Invoking timeservice.");
            return Response.ok(dateTimeNowSupplier.get()).build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("We were not able to get the current time at this moment.")
                    .build();
        }
    }
}