package no.cx.iot.facade.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SDKAdapterTest {

    @InjectMocks
    private SDKAdapter sdkAdapter;

    @Test
    public void fakeBridgeWhenPropertyFalse() {
        assertThat(sdkAdapter.getBridge(), is(instanceOf(FakeBridge.class)));
    }

    @Test
    public void isBridgeSelectedTrueForFakeBridge() {
        assertThat(sdkAdapter.isBridgeSelected(), is(true));
    }

    @Test
    public void searchWithFakeBridgeDoesNothing() {
        sdkAdapter.search(true, false);
    }

}