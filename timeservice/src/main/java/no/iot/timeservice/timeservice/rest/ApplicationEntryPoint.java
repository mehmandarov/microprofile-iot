package no.iot.timeservice.timeservice.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/timeservice")
public class ApplicationEntryPoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TimeServiceEndpoint.class);
        return classes;
    }
}
