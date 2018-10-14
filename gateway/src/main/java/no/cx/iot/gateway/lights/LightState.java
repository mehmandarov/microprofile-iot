package no.cx.iot.gateway.lights;

import java.awt.Color;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.cx.iot.gateway.InputSource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LightState {
    private Integer lightIndex;
    private InputSource inputSource;
    private Brightness brightness;
    private Integer hueInt;

    public Color getHue() {
        return Optional.ofNullable(hueInt)
                .map(Color::new)
                .orElse(Color.RED);
    }

    public int getBrightnessInt() {
        return brightness.getBrightness();
    }
}
