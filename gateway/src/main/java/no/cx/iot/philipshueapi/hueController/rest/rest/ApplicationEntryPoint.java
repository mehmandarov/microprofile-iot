package no.cx.iot.philipshueapi.hueController.rest.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.google.common.collect.Sets;

@ApplicationPath("/hue")
public class ApplicationEntryPoint extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		return Sets.newHashSet(RootResource.class, VerifyResource.class);
	}
}