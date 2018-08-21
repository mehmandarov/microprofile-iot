package no.iot.weatherservice.weather.yr;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.iot.weatherservice.weather.Temperature;
import no.iot.weatherservice.cache.WeatherCacheHandler;
import no.iot.weatherservice.utils.general.HttpConnector;
import no.iot.weatherservice.weather.WeatherInputProvider;

import static no.iot.weatherservice.utils.general.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
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
    public Temperature getTemperature() {
        Optional<Temperature> temperatureFromNewlyUpdatedCache = weatherCacheHandler.get(currentLocation);
        if (temperatureFromNewlyUpdatedCache.isPresent()) {
            return temperatureFromNewlyUpdatedCache.get();
        }

        Temperature temperature = getTemperatureFromYr();
        weatherCacheHandler.updateCache(currentLocation, temperature);
        return temperature;
    }

    private Temperature getTemperatureFromYr() {
        String responseFromYr = wrapExceptions(() -> connector.executeHTTPGet(yrURLProvider.getURL()));
        return new Temperature(xmlToTemperatureConverter.convert(responseFromYr));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
