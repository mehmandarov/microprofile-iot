package no.cx.iot.philipshueapi.hueController.rest.weather;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
@Getter
public class WeatherURLProvider {

    @Inject
    @ConfigProperty(name = "weatherHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "weatherPort", defaultValue = "8082")
    private String port;

    @Inject
    @ConfigProperty(name = "weatherPath", defaultValue = "weatherservice")
    private String path;

    String getFullURL() {
        return "http://" + getHost() + ":" + getPort() +"/" + getPath();
    }
}
