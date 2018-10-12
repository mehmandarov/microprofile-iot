package no.cx.iot.philipshueapi.hueAPI.logic;

import java.util.List;
import java.util.Optional;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.philipshueapi.hueAPI.bridge.Bridge;
import no.cx.iot.philipshueapi.hueAPI.lightstate.Brightness;
import no.cx.iot.philipshueapi.hueAPI.lightstate.LightState;

class LightStateGetter {

    PHLight getGivenLight(Bridge bridge, int lightIndex) {
        return getAllLights(bridge).get(lightIndex);
    }

    List<PHLight> getAllLights(Bridge selectedBridge) {
        return selectedBridge.getAllLights();
    }

    LightState getLightState(Bridge bridge, int lightIndex, PHLightState lightState) {
        Brightness brightness = Optional.ofNullable(lightState.getBrightness())
                .map(Brightness::new)
                .orElseGet(Brightness::getMaxBrightness);
        return new LightState(lightIndex, bridge.getInputSource(), brightness, lightState.getHue());
    }

    PHLightState getLastKnownLightState(Bridge bridge, int lightIndex, PHLight light) {
        PHLightState lastKnownLightState = bridge.getLastKnownLightState(light);
        if (lastKnownLightState == null || !lastKnownLightState.isReachable()) {
            throw new RuntimeException("Light " + lightIndex + " is not reachable.");
        }
        return lastKnownLightState;
    }
}
