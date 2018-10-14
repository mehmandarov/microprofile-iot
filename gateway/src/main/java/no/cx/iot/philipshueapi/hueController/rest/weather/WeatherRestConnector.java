package no.cx.iot.philipshueapi.hueController.rest.weather;

import java.io.IOException;

import javax.inject.Inject;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.infrastructure.HttpConnector;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

public class WeatherRestConnector implements InputProvider<Weather> {

    @Inject
    private HttpConnector connector;

    @Inject
    private WeatherURLProvider weatherURLProvider;

    @Inject
    @Getter
    private WeatherToLightStateConverter converter;

    @Override
    public Weather getDataForLight(int lightIndex) {
        return wrapExceptions(this::getWeather);
    }

    @Override
    public void testConnection() throws IOException {
        getWeather();
    }

    @Override
    public int getPriority() {
        return InputSource.WEATHER.getPriority();
    }

    private Weather getWeather() throws IOException {
        return connector.executeHTTPGet(weatherURLProvider.getFullURL(), Weather.class);
    }

}
