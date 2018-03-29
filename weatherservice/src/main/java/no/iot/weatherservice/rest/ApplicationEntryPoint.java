package no.iot.weatherservice.rest;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/weatherservice")
public class ApplicationEntryPoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(WeatherServiceEndpoint.class);
    }
}
