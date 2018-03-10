package no.cx.iot.philipshueapi.hueAPI.logic;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.philipshueapi.hueAPI.HueAPIException;
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

    public LightState switchStateOfGivenLight(PHBridge bridge, int lightIndex, int brightness) {
        PHLight light = getGivenLight(bridge, lightIndex);
        PHLightState lastKnownLightState = getLastKnownLightState(lightIndex, light);
        logger.fine("New brightness: " + brightness);
        lastKnownLightState.setBrightness(brightness);
        //bridge.updateLightState(light, lastKnownLightState);
        return getLightState(lastKnownLightState);
    }

    private LightState getLightState(PHLightState lightState) {
        Brightness brightness = Optional.ofNullable(lightState.getBrightness())
                .map(Brightness::new)
                .orElseGet(Brightness::getMaxBrightness);
        return new LightState(InputSource.LIGHT, brightness, lightState.getHue());
    }

    private PHLightState getLastKnownLightState(int lightIndex, PHLight light) {
        PHLightState lastKnownLightState = light.getLastKnownLightState();
        if (!lastKnownLightState.isReachable()) {
            throw new HueAPIException("Light " + lightIndex + " is not reachable.");
        }
        return lastKnownLightState;
    }

    PHLight getGivenLight(PHBridge bridge, int lightIndex) {
        return getAllLights(bridge).get(lightIndex);
    }

    public int getNumberOfLights() {
        return getAllLights(sdk.getSelectedBridge()).size();
    }

    private List<PHLight> getAllLights(PHBridge selectedBridge) {
        return selectedBridge.getResourceCache().getAllLights();
    }
}
