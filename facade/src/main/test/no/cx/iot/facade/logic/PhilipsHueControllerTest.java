package no.cx.iot.facade.logic;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.hue.sdk.utilities.impl.Color;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.sdk.Bridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class PhilipsHueControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private LightStateGetter lightStateGetter;

    @Mock
    private Logger logger;

    @Mock
    private ColourSetter colourSetter;

    @Mock
    private Bridge bridge;

    @InjectMocks
    private PhilipsHueController philipsHueController;

    @Test
    public void colorConversionTest() {
        int magentaHue = Color.MAGENTA;
        java.awt.Color magentaJava = java.awt.Color.MAGENTA;
        int rgb = Color.rgb(magentaJava.getRed(), magentaJava.getGreen(), magentaJava.getBlue());
        assertEquals(magentaHue, rgb);

        float[] calculateXY = PHUtilities.calculateXYFromRGB(200, 0, 200, PHLight.PHLightColorMode.COLORMODE_XY.getValue());
        int colorFromXY = PHUtilities.colorFromXY(calculateXY, PHLight.PHLightColorMode.COLORMODE_XY.getValue());
        java.awt.Color color = new java.awt.Color(colorFromXY);
        assertEquals(color, new java.awt.Color(254, 0, 254));
    }

    @Test
    public void wontInvokeNonReachableLight() {
        PHLight nonReachableLight = mock(PHLight.class);
        PHLightState nonReachableLightsState = mock(PHLightState.class);
        doReturn(nonReachableLight).when(lightStateGetter).getGivenLight(eq(bridge), anyInt());
        doCallRealMethod().when(lightStateGetter).getLastKnownLightState(eq(bridge), anyInt(), eq(nonReachableLight));

        try {
            philipsHueController.switchStateOfGivenLight(bridge, 0, 0, 0);
            fail();
        }
        catch (RuntimeException e) {
            verify(nonReachableLightsState, never()).setBrightness(anyInt());
        }
    }

}