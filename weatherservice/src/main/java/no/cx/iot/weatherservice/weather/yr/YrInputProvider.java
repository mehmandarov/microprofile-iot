package no.cx.iot.weatherservice.weather.yr;

import java.net.URL;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import no.cx.iot.weatherservice.cache.CacheHandler;
import no.cx.iot.weatherservice.weather.InputProvider;
import no.cx.iot.weatherservice.weather.Temperature;

import static no.cx.iot.weatherservice.utils.ExceptionWrapper.wrapExceptions;

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
    @Inject
    private Logger logger;

    @Override
    public Temperature getTemperature() {
        logger.info("Getting temperature for " + yrCityPart + ", " + country);
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
        logger.info("Getting temperature from " + yrCityPart + ", " + country);

        String weather = RestClientBuilder.newBuilder()
                .baseUrl(wrapExceptions(() -> new URL("http://www.yr.no/")))
                .build(YrService.class)
                .getWeather(country, yrCityPart);


        return new Temperature(xmlToTemperatureConverter.convert(weather));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
