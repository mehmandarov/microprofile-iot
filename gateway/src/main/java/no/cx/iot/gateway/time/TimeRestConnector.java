package no.cx.iot.gateway.time;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import lombok.Getter;
import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.ExceptionWrapper;

@ApplicationScoped
public class TimeRestConnector implements InputProvider<ZonedDateTime> {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "9081")
    private String port;

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
                .baseUrl(new URL("http://" + host + ":" + host))
                .build(TimeEndpoint.class)
                .getCurrentTime()
                .getDateTime();
    }

    @Override
    public ZonedDateTime getDataForLight(int lightIndex) {
        return ExceptionWrapper.wrapExceptions(this::getTime);
    }
}
