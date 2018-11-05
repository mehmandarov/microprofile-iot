package no.cx.iot.gateway.weather;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
@Getter
public class WeatherURLProvider {

    @Inject
    @ConfigProperty(name = "weatherHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "weatherPort", defaultValue = "8082")
    private String port;

    URL getBaseURL() throws MalformedURLException {
        return new URL("http://" + getHost() + ":" + getPort());
    }
}
