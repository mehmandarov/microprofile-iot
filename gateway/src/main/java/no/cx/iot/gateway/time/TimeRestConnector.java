package no.cx.iot.gateway.time;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.Getter;
import no.cx.iot.gateway.infrastructure.ExceptionWrapper;
import no.cx.iot.gateway.infrastructure.HttpConnector;
import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;

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
    public void testConnection() throws IOException {
        getTime();
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
        return ExceptionWrapper.wrapExceptions(this::getTime);
    }
}
