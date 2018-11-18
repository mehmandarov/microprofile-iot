package no.cx.iot.weatherservice.weather.yr;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import no.cx.iot.weatherservice.cache.CacheHandler;
import no.cx.iot.weatherservice.weather.InputProvider;
import no.cx.iot.weatherservice.weather.Temperature;

@ApplicationScoped
@SuppressWarnings("unused")
public class YrInputProvider implements InputProvider {

    @Inject
    @ConfigProperty(name = "country")
    private String country;
    @Inject
    @ConfigProperty(name = "yr_city_part")
    private String yrCityPart;
    @Inject
    private XMLToTemperatureConverter xmlToTemperatureConverter;
    @Inject
    private CacheHandler cacheHandler;

    @Override
    public Temperature getTemperature() {
        return cacheHandler
                .get(yrCityPart)
                .orElseGet(() -> {
                    Temperature temperature = getTemperatureFromYr();
                    cacheHandler.updateCache(yrCityPart, temperature);
                    return temperature;
                });
        }

    @Timeout(2000)
    private Temperature getTemperatureFromYr() {
        String weather = RestClientBuilder.newBuilder()
                .baseUrl(getURL())
                .build(YrService.class)
                .getWeather(country, yrCityPart);

        return new Temperature(xmlToTemperatureConverter.convert(weather));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }


    private URL getURL() {
        try {
            return new URL("http://www.yr.no/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
