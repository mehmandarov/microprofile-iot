package no.iot.timeservice.timeservice.rest;


import no.iot.timeservice.timeservice.supplier.LocalDateTimeNowSupplier;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

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