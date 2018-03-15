package no.cx.iot.philipshueapi.hueAPI;

import java.util.ArrayList;
import java.util.List;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import lombok.RequiredArgsConstructor;
import no.cx.iot.philipshueapi.hueAPI.logic.Bridge;

@RequiredArgsConstructor
public class DummyBridge implements Bridge {
    private final PHBridge selectedBridge;


    @Override
    public PHBridgeResourcesCache getResourceCache() {
        return selectedBridge.getResourceCache();
    }

    @Override
    public List<PHLight> getAllLights() {
        return new ArrayList<>();
    }

    @Override
    public void updateLightState(PHLight light, PHLightState lastKnownLightState) {

    }
}
