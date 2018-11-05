package no.cx.iot.gateway.weather;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import lombok.Getter;
import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.ExceptionWrapper;

public class WeatherRestConnector implements InputProvider<Weather> {

    @Inject
    private WeatherURLProvider weatherURLProvider;

    @Inject
    @Getter
    private WeatherToLightStateConverter converter;

    @Override
    public Weather getDataForLight(int lightIndex) {
        return ExceptionWrapper.wrapExceptions(this::getWeather);
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
        return RestClientBuilder.newBuilder()
                .baseUrl(weatherURLProvider.getBaseURL())
                .build(WeatherEndpoint.class)
                .getWeather();

    }

}
