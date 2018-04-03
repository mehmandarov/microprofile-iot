package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.infrastructure.HttpConnector;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
public class TimeRestConnector implements InputProvider<ZonedDateTime> {

    @Inject
    private TimeURLProvider timeURLProvider;

    @Inject
    @Getter
    private TimeToLightStateConverter converter;

    @Inject
    private HttpConnector connector;

    @Override
    public boolean testConnection() throws IOException {
        getTime();
        return true;
    }

    @Override
    public int getPriority() {
        return InputSource.TIME.getPriority();
    }

    private ZonedDateTime getTime() throws IOException {
        return connector.executeHTTPGet(timeURLProvider.getFullURL(), TimeDTO.class).getDateTime();
    }

    @Override
    public ZonedDateTime getDataForLight(int lightIndex) {
        return wrapExceptions(this::getTime);
    }
}
