package no.cx.iot.gateway.time;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import lombok.Getter;
import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.ExceptionWrapper;

@ApplicationScoped
@Traced
public class TimeRestConnector implements InputProvider<ZonedDateTime> {

    @Inject
    private TimeURLProvider timeURLProvider;

    @Inject
    @Getter
    private TimeToLightStateConverter converter;


    @Override
    public void testConnection() throws IOException {
        getTime();
    }

    @Override
    public int getPriority() {
        return InputSource.TIME.getPriority();
    }

    private ZonedDateTime getTime() throws IOException {
        return RestClientBuilder.newBuilder()
                .baseUrl(timeURLProvider.getBaseURL())
                .build(TimeEndpoint.class)
                .getCurrentTime()
                .getDateTime();
    }

    @Override
    public ZonedDateTime getDataForLight(int lightIndex) {
        return ExceptionWrapper.wrapExceptions(this::getTime);
    }
}
