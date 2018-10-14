package no.cx.iot.facade.bridge;

import java.util.List;

import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.lightstate.InputSource;

public interface Bridge {
    PHBridgeResourcesCache getResourceCache();

    void updateLightState(PHLight light, PHLightState lastKnownLightState);

    default List<PHLight> getAllLights() {
        return getResourceCache().getAllLights();
    }

    default PHLightState getLastKnownLightState(PHLight light) {
        return light.getLastKnownLightState();
    }

    InputSource getInputSource();
}
