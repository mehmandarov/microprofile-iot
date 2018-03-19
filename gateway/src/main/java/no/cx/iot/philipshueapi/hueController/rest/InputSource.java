package no.cx.iot.philipshueapi.hueController.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InputSource {
    WEATHER(20), TIME(10), COMPUTED(0), LIGHT(-1);

    private final Integer priority;
}
