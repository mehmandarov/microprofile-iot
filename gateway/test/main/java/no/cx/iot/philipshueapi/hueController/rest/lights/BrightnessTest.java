package no.cx.iot.philipshueapi.hueController.rest.lights;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BrightnessTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private Brightness brightness;

    @Test
    public void brightness200IsOK() {
        assertThat(createBrightness(200).getBrightness(), is(200));
    }

    private Brightness createBrightness(int i) {
        brightness.setBrightness(i);
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

    @Test
    public void equalsIgnoresLogger() {
        assertThat(new Brightness(50), is(new Brightness(50)));
    }
}