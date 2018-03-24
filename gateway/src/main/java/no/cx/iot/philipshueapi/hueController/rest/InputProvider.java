package no.cx.iot.philipshueapi.hueController.rest;

import java.io.IOException;
import java.util.Optional;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public interface InputProvider<T> {

    default String getFullURL() {
        return "http://" + getHost() + ":" + getPort() +"/" + getPath();
    }

    Converter<T> getConverter();

    T getDataForLight(int lightIndex);

    default LightState getNewStateForLight(int lightIndex) {
        return Optional.ofNullable(getDataForLight(lightIndex))
                .map(data -> getConverter().convert(lightIndex, data))
                .orElse(null);
    }

    default void canConnect() {
        try {
            testConnection();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void testConnection() throws IOException;

    String getHost();

    String getPort();

    String getPath();

    int getPriority();
}
