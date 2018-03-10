package no.cx.iot.philipshueapi.hueAPI.logic;

import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public interface Bridge {
    PHBridgeResourcesCache getResourceCache();

    void updateLightState(PHLight light, PHLightState lastKnownLightState);
}
