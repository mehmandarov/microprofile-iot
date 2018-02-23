package no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider;

import lombok.Data;

@Data
public class Tuple<A, B> {
    private final A first;
    private final B second;
}
