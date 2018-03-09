package no.cx.iot.philipshueapi.hueController.rest;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.philipshueapi.hueController.rest.hueAPI.PhilipsHueConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightStateComputer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LightStateSwitcherTest {

    @Mock
    private PhilipsHueConnector connector;

    @Mock
    private LightStateComputer lightStateComputer;

    @Mock
    private Logger logger;

    @InjectMocks
    private LightStateSwitcher lightStateSwitcher;

    @Mock
    private LightState lightState;

    @Test
    public void forwardsToController() throws IOException {
        doReturn(2).when(connector).getAllLights();
        lightStateSwitcher.switchStateOfLights();
        verify(connector).switchStateOfLight(eq(0), any());
        verify(connector).switchStateOfLight(eq(1), any());
    }

    @Test
    public void usesTheProposedLightStateFromTheLightStateComputer() throws IOException {
        doReturn(1).when(connector).getAllLights();
        doReturn(lightState).when(lightStateComputer).getNewStateForLight(any());

        lightStateSwitcher.switchStateOfLights();
        verify(connector).switchStateOfLight(0, lightState);
    }

}