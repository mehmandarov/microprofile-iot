package no.iot.timeservice;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;

import no.iot.timeservice.supplier.ZonedDateTimeNowSupplier;


@Path("/")
public class TimeServiceEndpoint {

    @SuppressWarnings("unused")
    @Inject
    private ZonedDateTimeNowSupplier dateTimeNowSupplier;

    @Inject
    private Logger logger;

    @Inject
    @ConfigProperty(name = "errorMessage")
    private String errorMessage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets the current time", description = "The format is a wrapped ZonedDateTime")
    public Response getCurrentTime() {
        try {
            logger.info("Invoking timeservice.");
            return Response
                    .ok(dateTimeNowSupplier.get())
                    .build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(errorMessage)
                    .build();
        }
    }
}