package no.cx.iot.timeservice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;


@Path("")
@Schema(name = "Timeservice")
@ApplicationScoped
public class TimeServiceEndpoint {

    @SuppressWarnings("unused")
    @Inject
    private ZonedDateTimeNowSupplier dateTimeNowSupplier;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(description = "The current time")
    @Operation(summary = "Gets the current time", description = "The format is a wrapped ZonedDateTime")
    public TimeDTO getCurrentTime() {
        return dateTimeNowSupplier.get();
    }
}