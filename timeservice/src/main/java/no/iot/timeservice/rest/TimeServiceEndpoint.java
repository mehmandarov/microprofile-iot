package no.iot.timeservice.rest;

import no.iot.timeservice.supplier.LocalDateTimeNowSupplier;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.logging.Logger;


@Path("/")
public class TimeServiceEndpoint {

    @SuppressWarnings("unused")
    @Inject
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    private Logger logger = Logger.getLogger(getClass().getSimpleName());


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        logger.info(String.format("Invoking timeservice. Response."));
        return Response.ok(localDateTimeNowSupplier.get()).build();
    }
}