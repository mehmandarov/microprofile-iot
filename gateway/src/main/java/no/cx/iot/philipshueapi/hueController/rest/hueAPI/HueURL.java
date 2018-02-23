package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HueURL {

    @Inject
    @ConfigProperty(name="host")
    private String host;

    @Inject
    @ConfigProperty(name="port", defaultValue = "8081")
    private String port;

    String getFullURL() {
        return "http://" + host + ":" + port +"/hue/";
    }
}
