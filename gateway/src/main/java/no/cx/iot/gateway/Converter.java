package no.cx.iot.gateway;

import no.cx.iot.gateway.lights.LightState;

@FunctionalInterface
public interface Converter<T> {
    LightState convert(int lightIndex, T t);
}
