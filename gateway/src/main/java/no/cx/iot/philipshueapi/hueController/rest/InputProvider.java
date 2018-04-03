package no.cx.iot.philipshueapi.hueController.rest;

import java.io.IOException;
import java.util.Optional;

import no.cx.iot.philipshueapi.hueController.rest.infrastructure.Connector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public interface InputProvider<T> extends Connector {

    Converter<T> getConverter();

    T getDataForLight(int lightIndex);

    default LightState getNewStateForLight(int lightIndex) {
        return Optional.ofNullable(getDataForLight(lightIndex))
                .map(data -> getConverter().convert(lightIndex, data))
                .orElse(null);
    }

    default boolean canConnect() {
        try {
            testConnection();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    boolean testConnection() throws IOException;

    int getPriority();
}
