package no.iot.timeservice.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import no.iot.timeservice.supplier.LocalDateTimeNowSupplier;


@Path("/")
public class TimeServiceEndpoint {

    @SuppressWarnings("unused")
    @Inject
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    private Logger logger = Logger.getLogger(getClass().getSimpleName());


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets the current time",
            description = "The format is a wrapped LocalDateTime")
    public Response doGet() {
        try {
            logger.info(String.format("Invoking timeservice. Response."));
            return Response.ok(localDateTimeNowSupplier.get()).build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("We were not able to get the current time at this moment.")
                    .build();
        }
    }
}