package no.cx.iot.gateway.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

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