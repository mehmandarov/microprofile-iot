package no.cx.iot.philipshueapi.hueController.rest.facade;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.philipshueapi.hueController.rest.infrastructure.HttpConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FacadeConnectorTest {

    @Mock
    private HttpConnector httpConnector;

    @Mock
    private FacadeURL facadeURL;

    @Spy
    private FacadePathComposer facadePathComposer;

    @InjectMocks
    private FacadeConnector facadeConnector;

    @Test
    public void hueURLIsComposedProperly() throws IOException {
        doReturn("localhozt/").when(facadeURL).getFullURL();
        LightState newLightState = new LightState();
        newLightState.setLightIndex(1);
        newLightState.setBrightness(new Brightness(2));
        newLightState.setHueInt(3);

        LightState s = facadeConnector.switchStateOfLight(newLightState);

        verify(httpConnector).executeHTTPGet("localhozt/light/1/brightness/2/color/3", LightState.class);
    }

}