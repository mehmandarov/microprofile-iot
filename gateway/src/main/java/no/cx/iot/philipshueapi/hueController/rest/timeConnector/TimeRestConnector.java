package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
@Getter
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
}
