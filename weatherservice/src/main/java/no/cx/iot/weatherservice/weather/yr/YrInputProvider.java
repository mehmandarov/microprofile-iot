package no.cx.iot.weatherservice.weather.yr;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.weatherservice.cache.CacheHandler;
import no.cx.iot.weatherservice.utils.general.ExceptionWrapper;
import no.cx.iot.weatherservice.utils.general.HttpConnector;
import no.cx.iot.weatherservice.weather.Temperature;
import no.cx.iot.weatherservice.weather.InputProvider;

@ApplicationScoped
@SuppressWarnings("unused")
public class YrInputProvider implements InputProvider {

    @Inject
    @ConfigProperty(name = "location", defaultValue = "Oslo")
    private String currentLocation;
    @Inject
    private YrURLProvider yrURLProvider;
    @Inject
    private HttpConnector connector;
    @Inject
    private XMLToTemperatureConverter xmlToTemperatureConverter;
    @Inject
    private CacheHandler cacheHandler;

    @Override
    public Temperature getTemperature() {
        Optional<Temperature> temperatureFromNewlyUpdatedCache = cacheHandler.get(currentLocation);
        if (temperatureFromNewlyUpdatedCache.isPresent()) {
            return temperatureFromNewlyUpdatedCache.get();
        }

        Temperature temperature = getTemperatureFromYr();
        cacheHandler.updateCache(currentLocation, temperature);
        return temperature;
    }

    private Temperature getTemperatureFromYr() {
        String responseFromYr = ExceptionWrapper.wrapExceptions(() -> connector.executeHTTPGet(yrURLProvider.getURL()));
        return new Temperature(xmlToTemperatureConverter.convert(responseFromYr));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
