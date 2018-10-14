package no.cx.iot.gateway.facade;

import org.junit.Test;

import no.cx.iot.gateway.lights.Brightness;
import no.cx.iot.gateway.lights.LightState;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FacadePathComposerTest {

    @Test
    public void hueURLIsComposedProperly() {
        LightState newLightState = new LightState();
        newLightState.setLightIndex(1);
        newLightState.setBrightness(new Brightness(2));
        newLightState.setHueInt(3);

        assertThat(new FacadePathComposer().composePath(newLightState), is("light/1/brightness/2/color/3"));

    }
}