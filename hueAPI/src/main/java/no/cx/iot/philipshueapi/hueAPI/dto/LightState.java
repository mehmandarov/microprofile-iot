package no.cx.iot.philipshueapi.hueAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LightState {
    private final int lightIndex;
    private final InputSource inputSource;
    private final Brightness brightness;
    private Integer hueInt;
}
