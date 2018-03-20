package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@Getter
public class WeatherRestConnector implements InputProvider<Weather> {

    @Inject
    @ConfigProperty(name = "weatherHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "weatherPort", defaultValue = "8082")
    private String port;

    @Inject
    @ConfigProperty(name = "weatherPath", defaultValue = "weatherservice")
    private String path;

    @Inject
    private HttpConnector connector;

    @Inject
    private WeatherToLightStateConverter converter;

    @Override
    public Weather getDataForLight(int lightIndex) {
        return getWeather();
    }

    @Override
    public void testConnection() {
        getWeather();
    }

    @Override
    public int getPriority() {
        return InputSource.WEATHER.getPriority();
    }

    private Weather getWeather() {
        return wrapExceptions(() -> connector.executeHTTPGet(getFullURL(), Weather.class));
    }

}
