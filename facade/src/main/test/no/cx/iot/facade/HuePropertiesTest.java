package no.cx.iot.facade;

import org.junit.Test;

public class HuePropertiesTest {
    @Test
    public void canHandleThatFileDoesNotExist() {
        HueProperties hueProperties = new HueProperties();
        hueProperties.setUp();
        hueProperties.storeConnectionData("a", "b");
    }
}