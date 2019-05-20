package no.cx.iot.gateway.weather;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import no.cx.iot.gateway.InputProvider;
import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.Printer;

import static no.cx.iot.gateway.infrastructure.ExceptionWrapper.wrapExceptions;

public class WeatherRestConnector implements InputProvider<Weather> {

    @Inject
    @ConfigProperty(name = "weatherHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "weatherPort", defaultValue = "8082")
    private String port;

    @Inject
    private WeatherToLightStateConverter converter;

    @Inject
    private Printer printer;

    @Override
    public WeatherToLightStateConverter getConverter() {
        return converter;
    }

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
        URL baseURL = new URL("http://" + host + ":" + port);
        printer.println("Constructing weather endpoint at baseURL " + baseURL);
        WeatherEndpoint weatherEndpoint = RestClientBuilder.newBuilder()
                .baseUrl(baseURL)
                .build(WeatherEndpoint.class);
        printer.println("Weather endpoint: " + weatherEndpoint);
        return weatherEndpoint.getWeather();

    }

}
