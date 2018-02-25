package no.cx.iot.philipshueapi.hueController.rest;

import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class TimeRestConnector {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "8082")
    private String port;

    @Inject
    @ConfigProperty(name = "timePath", defaultValue = "timeservice")
    private String path;

    private String getFullURL() {
        return "http://" + host + ":" + port +"/" + path;
    }

    @Inject
    private HttpConnector connector;

    public String canConnect() {
        try {
            return "OK, the current time is " + getTime();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getTime() throws IOException {
        return connector.executeHTTPGet(getFullURL());
    }
}
