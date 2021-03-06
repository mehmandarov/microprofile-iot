package no.cx.iot.gateway.lights;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import no.cx.iot.gateway.InputSource;
import no.cx.iot.gateway.infrastructure.Printer;
import no.cx.iot.gateway.time.TimeRestConnector;
import no.cx.iot.gateway.weather.WeatherRestConnector;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProposedLightStatesFinderTest {

    @Mock
    private Printer printer;

    @Spy
    private TimeRestConnector timeRestConnector;

    @Spy
    private WeatherRestConnector weatherRestConnector;

    @InjectMocks
    private ProposedLightStatesFinder lightStatesFinder;

    @Mock
    private LightState weatherLightState;

    @Mock
    private LightState timeLightState;

    @Test
    public void usesTheInputProviderWithHighestPriorityAvailable() {
        lightStatesFinder.registerInputProviders();
        doReturn(true).when(weatherRestConnector).canConnect();
        doReturn(true).when(timeRestConnector).canConnect();
        doReturn(weatherLightState).when(weatherRestConnector).getNewStateForLight(0);
        doReturn(timeLightState).when(timeRestConnector).getNewStateForLight(0);
        doReturn(InputSource.WEATHER).when(weatherLightState).getInputSource();
        doReturn(InputSource.TIME).when(timeLightState).getInputSource();

        assertThat(lightStatesFinder.getNewStateForLight(0), is(weatherLightState));
    }

    @Test
    public void usesTheInputProviderChosesSecondIfFirstPriorityNotAvailable() {
        lightStatesFinder.registerInputProviders();
        doReturn(true).when(timeRestConnector).canConnect();
        doReturn(timeLightState).when(timeRestConnector).getNewStateForLight(0);
        doReturn(InputSource.WEATHER).when(weatherLightState).getInputSource();
        doReturn(InputSource.TIME).when(timeLightState).getInputSource();

        assertThat(lightStatesFinder.getNewStateForLight(0), is(timeLightState));
    }


    @Test
    public void fallbacksToFullLightIfNoResponseFromAny() {
        assertThat(lightStatesFinder.getNewStateForLight(0),
                is(new LightState(0, InputSource.COMPUTED, Brightness.getMaxBrightness(), null)));
    }

}