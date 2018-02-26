package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
public class TimeRestConnector implements InputProvider<LocalDateTime> {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "8082")
    private String port;

    @Inject
    @ConfigProperty(name = "timePath", defaultValue = "timeservice")
    private String path;

    @Inject
    private TimeToLightStateConverter converter;

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

    private LocalDateTime getTime() throws IOException {
        return connector.executeHTTPGet(getFullURL(), TimeDTO.class).getLocalDateTime();
    }

    @Override
    public LocalDateTime getDataForLight(int lightIndex) {
        return wrapExceptions(this::getTime);
    }

    @Override
    public LightState getNewStateForLight(int lightIndex) {
        return converter.convert(getDataForLight(lightIndex));
    }
}
