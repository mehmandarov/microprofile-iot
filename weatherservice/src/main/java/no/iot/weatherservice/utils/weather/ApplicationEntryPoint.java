package no.iot.weatherservice.utils.weather;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import no.iot.weatherservice.WeatherServiceEndpoint;

@ApplicationPath("/weatherservice")
public class ApplicationEntryPoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(WeatherServiceEndpoint.class);
    }
}
