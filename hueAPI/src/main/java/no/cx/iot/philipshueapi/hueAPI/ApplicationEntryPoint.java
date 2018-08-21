package no.cx.iot.philipshueapi.hueAPI;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class ApplicationEntryPoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(FacadeEndpoint.class);
    }
}
