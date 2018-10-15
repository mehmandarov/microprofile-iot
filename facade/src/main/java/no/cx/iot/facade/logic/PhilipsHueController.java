package no.cx.iot.facade.logic;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.lightstate.LightState;
import no.cx.iot.facade.sdk.Bridge;

import static com.philips.lighting.model.PHLight.PHLightColorMode.COLORMODE_XY;

@SuppressWarnings("unused")
@ApplicationScoped
public class PhilipsHueController {

    @Inject
    private Logger logger;

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
        setColorOnLight(bridge, colour, light, lastKnownLightState);
    }

    public int getNumberOfLights() {
        return lightStateGetter.getAllLights(getBridge()).size();
    }

    private Bridge getBridge() {
        return bridgeConnector.getBridge();
    }

    private void setColorOnLight(Bridge bridge, int color, PHLight light, PHLightState lastKnownLightState) {
        lastKnownLightState.setColorMode(COLORMODE_XY);
        float[] xy = PHUtilities.calculateXY(color, COLORMODE_XY.getValue());
        lastKnownLightState.setX(xy[0], true);
        lastKnownLightState.setY(xy[1], true);
        bridge.updateLightState(light, lastKnownLightState);
    }
}