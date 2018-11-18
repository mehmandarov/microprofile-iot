package no.cx.iot.facade.lightstate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InputSource {
    WEATHER(2),
    TIME(1),
    COMPUTED(0),
    LIGHT(-1),
    FAKE(-2),
    ERROR(-3);

    private final Integer priority;
}
