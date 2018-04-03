package no.cx.iot.philipshueapi.hueController.rest.weatherConnector;

import javax.inject.Inject;

import lombok.Getter;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.InputSource;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@Getter
public class WeatherRestConnector implements InputProvider<Weather> {

    @Inject
    private HttpConnector connector;

    @Inject
    private WeatherURLProvider weatherURLProvider;

    @Inject
    private WeatherToLightStateConverter converter;

    @Override
    public Weather getDataForLight(int lightIndex) {
        return getWeather();
    }

    @Override
    public boolean testConnection() {
        getWeather();
        return true;
    }

    @Override
    public int getPriority() {
        return InputSource.WEATHER.getPriority();
    }

    private Weather getWeather() {
        return wrapExceptions(() -> connector.executeHTTPGet(weatherURLProvider.getFullURL(), Weather.class));
    }

}
