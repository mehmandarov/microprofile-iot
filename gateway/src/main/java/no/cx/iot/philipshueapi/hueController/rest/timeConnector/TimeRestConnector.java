package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
@Getter
public class TimeRestConnector implements InputProvider<ZonedDateTime> {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "8081")
    private String port;

    @Inject
    @ConfigProperty(name = "timePath", defaultValue = "timeservice")
    private String path;

    @Inject
    private TimeToLightStateConverter converter;

    @Inject
    private HttpConnector connector;

    @Override
    public void testConnection() throws IOException {
        getTime();
    }

    @Override
    public int getPriority() {
        return InputSource.TIME.getPriority();
    }

    private ZonedDateTime getTime() throws IOException {
        return connector.executeHTTPGet(getFullURL(), TimeDTO.class).getDateTime();
    }

    @Override
    public ZonedDateTime getDataForLight(int lightIndex) {
        return wrapExceptions(this::getTime);
    }
}
