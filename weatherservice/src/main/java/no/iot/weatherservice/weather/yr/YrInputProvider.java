package no.iot.weatherservice.weather.yr;

import no.iot.weatherservice.utils.general.HttpConnector;
import no.iot.weatherservice.utils.general.WeatherInputProvider;
import no.iot.weatherservice.weather.XMLToTemperatureConverter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

import static no.iot.weatherservice.utils.general.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
/*
 * Yes, this class is a bit vulnerable, and yes, the caching mechanism only supports one location.
 * But atm it's good enough
 */
@SuppressWarnings("unused")
public class YrInputProvider implements WeatherInputProvider {

    @Inject
    private Config config;
    @Inject
    @ConfigProperty(name = "location", defaultValue = "Oslo")
    private String currentLocation;
    @Inject
    @ConfigProperty(name = "yr_URL", defaultValue = "")
    private String yrURL;
    @Inject
    private HttpConnector connector;
    @Inject
    private XMLToTemperatureConverter xmlToTemperatureConverter;
    @Inject
    private YrCacheHandler yrCacheHandler;

    @Override
    public String getTemperature() {
        Optional<String> temperatureFromNewlyUpdatedCache = yrCacheHandler.getFromNewlyUpdatedCache(currentLocation);
        if (temperatureFromNewlyUpdatedCache.isPresent()) {
            return temperatureFromNewlyUpdatedCache.get();
        }

        String temperature = xmlToTemperatureConverter.convert(wrapExceptions(() -> connector.executeHTTPGet(yrURL)));
        yrCacheHandler.updateCache(currentLocation, temperature);
        return temperature;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
