package no.cx.iot.philipshueapi.hueController.rest;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

public interface InputProvider<T> {

    default String getFullURL() {
        return "http://" + getHost() + ":" + getPort() +"/" + getPath();
    }

    Converter<T> getConverter();

    T getDataForLight(int lightIndex);

    default LightState getNewStateForLight(int lightIndex) {
        return getConverter().convert(getDataForLight(lightIndex));
    }

    String canConnect();

    String getHost();

    String getPort();

    String getPath();

    int getPriority();
}
