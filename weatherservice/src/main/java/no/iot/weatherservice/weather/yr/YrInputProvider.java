package no.iot.weatherservice.weather.yr;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.iot.weatherservice.cache.WeatherCacheHandler;
import no.iot.weatherservice.utils.general.HttpConnector;
import no.iot.weatherservice.weather.WeatherInputProvider;

import static no.iot.weatherservice.utils.general.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
/*
 * Yes, this class is a bit vulnerable, and yes, the caching mechanism only supports one location.
 * But atm it's good enough
 */
@SuppressWarnings("unused")
public class YrInputProvider implements WeatherInputProvider {

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
    private WeatherCacheHandler weatherCacheHandler;

    @Override
    public String getTemperature() {
        Optional<String> temperatureFromNewlyUpdatedCache = weatherCacheHandler.get(currentLocation);
        if (temperatureFromNewlyUpdatedCache.isPresent()) {
            return temperatureFromNewlyUpdatedCache.get();
        }

        String temperature = xmlToTemperatureConverter.convert(wrapExceptions(() -> connector.executeHTTPGet(yrURLProvider.getURL())));
        weatherCacheHandler.updateCache(currentLocation, temperature);
        return temperature;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
