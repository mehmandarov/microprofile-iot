package no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider;

import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
        /*
         * Yes, this class is a bit vulnerable, and yes, the caching mechanism only supports one location.
         * But atm it's good enough
         */
@SuppressWarnings("unused")
public class YrInputProvider implements InputProvider {

    @Inject
    private HttpConnector connector;

    @Inject
    private XMLToTemperatureConverter xmlToTemperatureConverter;

    @Inject
    private YrCacheHandler yrCacheHandler;

    private final String currentLocation = "Oslo";

    private final String yrURL = "http://www.yr.no/place/Norge/Oslo/Oslo/Oslo/forecast.xml";

    @Override
    public LightState getNewStateForLight(int lightIndex) {
        Optional<LightState> temperatureFromNewlyUpdatedCache = yrCacheHandler.getFromNewlyUpdatedCache(currentLocation, this::getLightState);
        if (temperatureFromNewlyUpdatedCache.isPresent()) {
            return temperatureFromNewlyUpdatedCache.get();
        }

        String temperature = xmlToTemperatureConverter.convert(wrapExceptions(() -> connector.executeHTTPGet(yrURL)));
        yrCacheHandler.updateCache(currentLocation, temperature);
        return getLightState(temperature);
    }
    private LightState getLightState(String temperatureStr) {
        Double temperature = Double.valueOf(temperatureStr);
        int newBrightness = (int) Math.round(temperature);

        return new LightState(new Brightness(newBrightness), null);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
