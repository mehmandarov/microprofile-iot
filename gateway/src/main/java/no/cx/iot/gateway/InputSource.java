package no.cx.iot.gateway;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InputSource {
    WEATHER(20), TIME(10), COMPUTED(0), LIGHT(-1), FAKE(-2);

    private final Integer priority;
}
