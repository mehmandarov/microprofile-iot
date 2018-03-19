package no.cx.iot.philipshueapi.hueController.rest;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightStateComputer;
import no.cx.iot.philipshueapi.hueController.rest.timeConnector.TimeRestConnector;
import no.cx.iot.philipshueapi.hueController.rest.weatherConnector.WeatherRestConnector;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProposedLightStatesFinderTest {

    @Mock
    private Logger logger;

    @Spy
    @InjectMocks
    private TimeRestConnector timeRestConnector;

    @Spy
    private WeatherRestConnector weatherRestConnector;

    @InjectMocks
    private ProposedLightStatesFinder lightStatesFinder;

    @Mock
    private LightState weatherLightState;

    @Mock
    private LightStateComputer lightStateComputer;

    @Mock
    private LightState timeLightState;

    @Test
    public void usesTheInputProviderWithHighestPriorityAvailable() throws IOException {
        lightStatesFinder.addInputProvider(timeRestConnector);
        lightStatesFinder.addInputProvider(weatherRestConnector);
        doReturn(weatherLightState).when(weatherRestConnector).getNewStateForLight(0);
        doReturn(timeLightState).when(timeRestConnector).getNewStateForLight(0);
        doReturn(InputSource.WEATHER).when(weatherLightState).getInputSource();
        doReturn(InputSource.TIME).when(timeLightState).getInputSource();

        assertThat(lightStatesFinder.getNewStateForLight(0), is(weatherLightState));
    }

    @Test
    public void usesTheInputProviderChosesSecondIfFirstPriorityNotAvailable() throws IOException {
        lightStatesFinder.addInputProvider(timeRestConnector);
        lightStatesFinder.addInputProvider(weatherRestConnector);
        doReturn(null).when(weatherRestConnector).getNewStateForLight(0);
        doReturn(timeLightState).when(timeRestConnector).getNewStateForLight(0);
        doReturn(InputSource.WEATHER).when(weatherLightState).getInputSource();
        doReturn(InputSource.TIME).when(timeLightState).getInputSource();

        assertThat(lightStatesFinder.getNewStateForLight(0), is(timeLightState));
    }


    @Test
    public void fallbacksToFullLightIfNoResponseFromAny() throws IOException {
        assertThat(lightStatesFinder.getNewStateForLight(0),
                is(new LightState(0, InputSource.COMPUTED, Brightness.getMaxBrightness(), null)));
    }

    @Test
    public void t() {
        int a = 2;
        int b = 3;
        int rgb = Color.BLUE.getRGB();
        System.out.println(rgb);

    }

}