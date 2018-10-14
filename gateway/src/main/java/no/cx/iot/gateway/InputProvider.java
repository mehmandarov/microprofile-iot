package no.cx.iot.gateway;

import java.util.Optional;

import no.cx.iot.gateway.infrastructure.Connector;
import no.cx.iot.gateway.lights.LightState;

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
