package no.cx.iot.gateway.lights;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BrightnessTest {

    @InjectMocks
    private Brightness brightness;

    @Test
    public void brightness200IsOK() {
        assertThat(createBrightness(200).getBrightness(), is(200));
    }

    private Brightness createBrightness(int i) {
        brightness.setBrightnessValue(i);
        return brightness;
    }

    @Test
    public void negativeBrightnessSetsBrightness0() {
        assertThat(createBrightness(-1).getBrightness(), is(0));
    }

    @Test
    public void tooHighBrightnessSetsBrightness254() {
        assertThat(createBrightness(255).getBrightness(), is(254));
    }
}