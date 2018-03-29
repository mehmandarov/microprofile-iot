package no.iot.timeservice.utils;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import no.iot.timeservice.TimeServiceEndpoint;

@ApplicationPath("/timeservice")
public class ApplicationEntryPoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(TimeServiceEndpoint.class);
    }
}
