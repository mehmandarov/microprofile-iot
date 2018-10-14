package no.cx.iot.gateway.facade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class FacadeURL {

    @Inject
    @ConfigProperty(name="facadehost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name="port", defaultValue = "8083")
    private String port;

    String getFullURL() {
        return "http://" + host + ":" + port +"/hue/";
    }
}
