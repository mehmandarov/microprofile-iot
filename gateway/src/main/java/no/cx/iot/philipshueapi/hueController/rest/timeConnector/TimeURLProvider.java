package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
@Getter
public class TimeURLProvider {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "8081")
    private String port;

    @Inject
    @ConfigProperty(name = "timePath", defaultValue = "timeservice")
    private String path;

    String getFullURL() {
        return "http://" + getHost() + ":" + getPort() +"/" + getPath();
    }
}
