package no.iot.timeservice;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public TimeDTO getCurrentTime() {
        logger.info("Invoking timeservice.");
        return dateTimeNowSupplier.get();
    }
}