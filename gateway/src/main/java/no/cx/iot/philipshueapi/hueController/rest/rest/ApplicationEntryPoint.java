package no.cx.iot.philipshueapi.hueController.rest.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/hue")
public class ApplicationEntryPoint extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RootResource.class);
		classes.add(VerifyResource.class);
		return classes;
	}
}