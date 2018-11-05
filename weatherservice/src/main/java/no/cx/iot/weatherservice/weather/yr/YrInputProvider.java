package no.cx.iot.weatherservice.weather.yr;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Timeout;

import no.cx.iot.weatherservice.cache.CacheHandler;
import no.cx.iot.weatherservice.utils.general.HttpConnector;
import no.cx.iot.weatherservice.weather.InputProvider;
import no.cx.iot.weatherservice.weather.Temperature;

import static no.cx.iot.weatherservice.utils.general.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
@SuppressWarnings("unused")
public class YrInputProvider implements InputProvider {

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
        return cacheHandler
                .get(yrURLProvider.getCity())
                .orElseGet(() -> {
                    Temperature temperature = getTemperatureFromYr();
                    cacheHandler.updateCache(yrURLProvider.getCity(), temperature);
                    return temperature;
                });
        }

    @Timeout(2000)
    private Temperature getTemperatureFromYr() {
        String responseFromYr = wrapExceptions(() -> connector.executeHTTPGet(yrURLProvider.getURL()));
        return new Temperature(xmlToTemperatureConverter.convert(responseFromYr));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
