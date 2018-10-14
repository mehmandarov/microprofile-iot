package no.cx.iot.philipshueapi.hueController.rest.facade;

import org.junit.Test;

import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HuePathComposerTest {

    @Test
    public void hueURLIsComposedProperly() {
        LightState newLightState = new LightState();
        newLightState.setLightIndex(1);
        newLightState.setBrightness(new Brightness(2));
        newLightState.setHueInt(3);

        assertThat(new HuePathComposer().composePath(newLightState), is("light/1/brightness/2/color/3"));

    }
}