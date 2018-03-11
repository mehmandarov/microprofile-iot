package no.cx.iot.philipshueapi.hueAPI.logic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.hue.sdk.utilities.impl.Color;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.philipshueapi.hueAPI.HueAPIException;
import no.cx.iot.philipshueapi.hueAPI.HueProperties;
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKFacade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhilipsHueControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private SDKFacade sdkFacade;

    @Mock
    private PHNotificationManager phNotificationManager;

    @Mock
    private HueProperties hueProperties;

    @Mock
    private BridgeConnector bridgeConnector;

    @Spy
    private Listener listener;

    @InjectMocks
    private PhilipsHueController philipsHueController;

    @Before
    public void setUp() {
        Mockito.doReturn(phNotificationManager).when(sdkFacade).getNotificationManager();
    }

    @Test
    public void colorConversionTest() {
        int magentaHue = Color.MAGENTA;
        java.awt.Color magentaJava = java.awt.Color.MAGENTA;
        int rgb = Color.rgb(magentaJava.getRed(), magentaJava.getGreen(), magentaJava.getBlue());
        assertEquals(magentaHue, rgb);
    }

    @Test
    public void wontInvokeNonReachableLight() {
        philipsHueController = spy(philipsHueController);
        PHLight nonReachableLight = mock(PHLight.class);
        PHLightState nonReachableLightsState = mock(PHLightState.class);
        doReturn(nonReachableLightsState).when(nonReachableLight).getLastKnownLightState();
        doReturn(nonReachableLight).when(philipsHueController).getGivenLight(any(), anyInt());

        expectedException.expect(HueAPIException.class);
        philipsHueController.switchStateOfGivenLight(null, 0, 0, 0);

        verify(nonReachableLightsState, never()).setBrightness(any());
    }

}