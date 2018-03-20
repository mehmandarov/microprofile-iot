package no.cx.iot.philipshueapi.hueController.rest;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import java.io.IOException;

public interface InputProvider<T> {

    default String getFullURL() {
        return "http://" + getHost() + ":" + getPort() +"/" + getPath();
    }

    Converter<T> getConverter();

    T getDataForLight(int lightIndex);

    default LightState getNewStateForLight(int lightIndex) {
        return getConverter().convert(lightIndex, getDataForLight(lightIndex));
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
