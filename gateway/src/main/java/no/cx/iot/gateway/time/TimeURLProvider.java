package no.cx.iot.gateway.time;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
@Getter
public class TimeURLProvider {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "9081")
    private String port;

    URL getBaseURL() throws MalformedURLException {
        return new URL("http://" + getHost() + ":" + getPort());
    }
}
