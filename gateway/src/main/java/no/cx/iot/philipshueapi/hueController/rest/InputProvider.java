package no.cx.iot.philipshueapi.hueController.rest;

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

    int getPriority();
}
