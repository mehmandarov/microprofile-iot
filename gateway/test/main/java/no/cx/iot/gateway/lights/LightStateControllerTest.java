package no.cx.iot.gateway.lights;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.gateway.facade.FacadeConnector;
import no.cx.iot.gateway.infrastructure.Printer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LightStateControllerTest {

    @Mock
    private FacadeConnector connector;

    @Mock
    private ProposedLightStatesFinder lightStatesFinder;

    @Mock
    private Printer printer;

    @InjectMocks
    private LightStateController lightStateController;

    @Mock
    private LightState lightState;

    @Test
    public void forwardsToController() throws IOException {
        doReturn(2).when(connector).getNumberOfLights();
        doReturn(lightState).when(lightStatesFinder).getNewStateForLight(anyInt());
        doReturn(lightState).when(connector).switchStateOfLight(any());

        lightStateController.switchStateOfLights();

        verify(connector, times(2)).switchStateOfLight(any());
    }

}