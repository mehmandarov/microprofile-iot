package no.cx.iot.philipshueapi.hueController.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InputSource {
    WEATHER(1), TIME(2);

    private final Integer priority;
}
