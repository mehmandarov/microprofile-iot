package no.cx.iot.philipshueapi.hueAPI.logic;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.philipshueapi.hueAPI.bridge.Bridge;
import no.cx.iot.philipshueapi.hueAPI.bridge.DummyBridge;
import no.cx.iot.philipshueapi.hueAPI.HueAPIException;
import no.cx.iot.philipshueapi.hueAPI.bridge.SDKBridge;
import no.cx.iot.philipshueapi.hueAPI.dto.Brightness;
import no.cx.iot.philipshueapi.hueAPI.dto.InputSource;
import no.cx.iot.philipshueapi.hueAPI.dto.LightState;
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKFacade;

@ApplicationScoped
@SuppressWarnings("unused")
public class PhilipsHueController {

    @Inject
    private SDKFacade sdk;

    @Inject
    private SetupController setupController;

    @Inject
    private Logger logger;

    public void setup() {
        setupController.setup();
    }

    public LightState switchStateOfGivenLight(Bridge bridge, int lightIndex, int brightness, int color) {
        PHLight light = getGivenLight(bridge, lightIndex);
        PHLightState lastKnownLightState = getLastKnownLightState(lightIndex, light);
        logger.fine("New brightness: " + brightness);
        lastKnownLightState.setBrightness(brightness);
        bridge.updateLightState(light, lastKnownLightState);
        setColorOnLight(bridge, color, light, lastKnownLightState);
        return getLightState(lightIndex, lastKnownLightState);
    }

    private void setColorOnLight(Bridge bridge, int color, PHLight light, PHLightState lastKnownLightState) {
        lastKnownLightState.setColorMode(PHLight.PHLightColorMode.COLORMODE_XY);
        float[] xy = PHUtilities.calculateXY(color, PHLight.PHLightColorMode.COLORMODE_XY.getValue());
        lastKnownLightState.setX(xy[0], true);
        lastKnownLightState.setY(xy[1], true);
        bridge.updateLightState(light, lastKnownLightState);
    }

    private LightState getLightState(int lightIndex, PHLightState lightState) {
        Brightness brightness = Optional.ofNullable(lightState.getBrightness())
                .map(Brightness::new)
                .orElseGet(Brightness::getMaxBrightness);
        return new LightState(lightIndex, InputSource.LIGHT, brightness, lightState.getHue());
    }

    private PHLightState getLastKnownLightState(int lightIndex, PHLight light) {
        PHLightState lastKnownLightState = light.getLastKnownLightState();
        if (!lastKnownLightState.isReachable()) {
            throw new HueAPIException("Light " + lightIndex + " is not reachable.");
        }
        return lastKnownLightState;
    }

    PHLight getGivenLight(Bridge bridge, int lightIndex) {
        return getAllLights(bridge).get(lightIndex);
    }

    public int getNumberOfLights(boolean useRealBridge) {
        Bridge bridge = useRealBridge ? getSDKBridge(sdk.getSelectedBridge()) : new DummyBridge();
        return getAllLights(bridge).size();
    }

    private Bridge getSDKBridge(PHBridge selectedBridge) {
        return new SDKBridge(selectedBridge);
    }

    private List<PHLight> getAllLights(Bridge selectedBridge) {
        return selectedBridge.getAllLights();
    }
}
