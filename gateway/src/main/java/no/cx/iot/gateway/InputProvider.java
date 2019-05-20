package no.cx.iot.gateway;

import no.cx.iot.gateway.infrastructure.Connector;
import no.cx.iot.gateway.lights.LightState;

import static java.util.Optional.ofNullable;

public interface InputProvider<T> extends Connector {

    Converter<T> getConverter();

    T getDataForLight(int lightIndex);

    default LightState getNewStateForLight(int lightIndex) {
        return ofNullable(getDataForLight(lightIndex))
                .map(data -> getConverter().convert(lightIndex, data))
                .orElse(null);
    }

    int getPriority();
}
