package no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider;

import no.cx.iot.philipshueapi.hueController.rest.LightState;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class YrInputProviderTest {

    @Spy
    private YrCacheHandler yrCacheHandler;

    @Spy
    private HttpConnector connector;

    @InjectMocks
    private YrInputProvider yrInputProvider;

    @Test
    public void checkInput() {
        LightState newStateForLight = yrInputProvider.getNewStateForLight(0);
    }
}