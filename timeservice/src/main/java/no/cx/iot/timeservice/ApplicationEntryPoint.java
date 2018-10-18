package no.cx.iot.timeservice;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/timeservice")
@OpenAPIDefinition(info = @Info(
        title = "Timeservice",
        version = "1.0"
))
public class ApplicationEntryPoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(TimeServiceEndpoint.class);
    }
}
