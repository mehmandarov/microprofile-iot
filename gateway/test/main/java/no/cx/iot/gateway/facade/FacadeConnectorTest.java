package no.cx.iot.gateway.facade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.gateway.lights.Brightness;
import no.cx.iot.gateway.lights.LightState;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FacadeConnectorTest {

    @Mock
    private FacadeEndpoint facadeEndpoint;

    @Spy
    private FacadeURL facadeURL;

    @InjectMocks
    private FacadeConnector facadeConnector;

    @Test
    public void hueURLIsComposedProperly() {
        doReturn("localhozt/").when(facadeURL).getFullURL();
        LightState newLightState = new LightState();
        newLightState.setLightIndex(1);
        newLightState.setBrightness(new Brightness(2));
        newLightState.setHueInt(3);

        LightState s = facadeConnector.switchStateOfLight(newLightState);

        verify(facadeEndpoint).switchStateOfLight(1, 2, 3);
    }

}