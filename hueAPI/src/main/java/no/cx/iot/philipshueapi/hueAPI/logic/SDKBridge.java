package no.cx.iot.philipshueapi.hueAPI.logic;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SDKBridge implements Bridge {

    private final PHBridge selectedBridge;


    @Override
    public PHBridgeResourcesCache getResourceCache() {
        return selectedBridge.getResourceCache();
    }

    @Override
    public void updateLightState(PHLight light, PHLightState lastKnownLightState) {
        selectedBridge.updateLightState(light, lastKnownLightState);
    }
}
