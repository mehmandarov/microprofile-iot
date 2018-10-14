package no.cx.iot.facade.logic;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.lightstate.LightState;
import no.cx.iot.facade.sdk.Bridge;

@SuppressWarnings("unused")
@ApplicationScoped
public class PhilipsHueController {

    @Inject
    private Logger logger;

    @Inject
    private ColourSetter colourSetter;

    @Inject
    private LightStateGetter lightStateGetter;

    @Inject
    private BridgeConnector bridgeConnector;

    public void setup() {
        bridgeConnector.setup();
    }

    public LightState switchStateOfGivenLight(int lightIndex, int brightness, int colour) {
        Bridge bridge = getBridge();
        PHLight light = lightStateGetter.getGivenLight(bridge, lightIndex);
        PHLightState lastKnownLightState = lightStateGetter.getLastKnownLightState(bridge, lightIndex, light);
        updateLightState(bridge, brightness, colour, light, lastKnownLightState);
        return lightStateGetter.getLightState(bridge, lightIndex, lastKnownLightState);
    }

    private void updateLightState(Bridge bridge, int brightness, int colour, PHLight light, PHLightState lastKnownLightState) {
        logger.fine("New brightness: " + brightness);
        lastKnownLightState.setBrightness(brightness);
        bridge.updateLightState(light, lastKnownLightState);
        colourSetter.setColorOnLight(bridge, colour, light, lastKnownLightState);
    }

    public int getNumberOfLights() {
        return lightStateGetter.getAllLights(getBridge()).size();
    }

    private Bridge getBridge() {
        return bridgeConnector.getBridge();
    }
}