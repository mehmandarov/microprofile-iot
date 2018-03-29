package no.iot.weatherservice.utils.general;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Tuple<A, B> {
    private final A first;
    private final B second;
}
