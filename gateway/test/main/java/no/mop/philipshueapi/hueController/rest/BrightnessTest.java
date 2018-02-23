package no.cx.iot.philipshueapi.hueController.rest;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BrightnessTest {

    @Test
    public void brightness200IsOK() {
        assertThat(new Brightness(200).getBrightness(), is(200));
    }

    @Test
    public void negativeBrightnessSetsBrightness0() {
        assertThat(new Brightness(-1).getBrightness(), is(0));
    }

    @Test
    public void tooHighBrightnessSetsBrightness254() {
        assertThat(new Brightness(255).getBrightness(), is(254));
    }

}