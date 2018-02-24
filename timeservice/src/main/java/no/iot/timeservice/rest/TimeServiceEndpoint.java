package no.iot.timeservice.rest;

import no.iot.timeservice.supplier.LocalDateTimeNowSupplier;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class TimeServiceEndpoint {

    @Inject
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok(localDateTimeNowSupplier.get()).build();
    }
}