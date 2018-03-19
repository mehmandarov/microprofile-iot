package no.cx.iot.philipshueapi.hueAPI.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InputSource {
    WEATHER(20), TIME(10), COMPUTED(0), LIGHT(-1);

    private final Integer priority;
}
