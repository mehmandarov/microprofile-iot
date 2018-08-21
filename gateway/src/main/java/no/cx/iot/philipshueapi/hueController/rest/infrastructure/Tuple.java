package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class Tuple<A, B> {
    private final A first;
    private final B second;
}
