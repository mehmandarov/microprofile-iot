package no.cx.iot.facade.sdk;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.lightstate.InputSource;

public class SDKBridge implements Bridge {

    private final PHBridge selectedBridge;

    public SDKBridge(PHBridge selectedBridge) {
        this.selectedBridge = selectedBridge;
    }


    @Override
    public PHBridgeResourcesCache getResourceCache() {
        return selectedBridge.getResourceCache();
    }

    @Override
    public void updateLightState(PHLight light, PHLightState lastKnownLightState) {
        selectedBridge.updateLightState(light, lastKnownLightState);
    }

    @Override
    public InputSource getInputSource() {
        return InputSource.LIGHT;
    }
}
