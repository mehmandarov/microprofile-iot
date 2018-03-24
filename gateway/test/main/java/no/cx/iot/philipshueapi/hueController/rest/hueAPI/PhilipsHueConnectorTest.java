package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhilipsHueConnectorTest {

    @Mock
    private HttpConnector httpConnector;

    @InjectMocks
    private PhilipsHueConnector connector;

    @Test
    public void get() throws IOException {
        doReturn(4).when(httpConnector).executeHTTPGetOnHue(any(), any());
        assertThat(connector.getNumberOfLights(), is(4));
        verify(httpConnector).executeHTTPGetOnHue(any(), eq(Integer.class));
    }

}