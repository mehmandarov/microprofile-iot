package no.cx.iot.facade.bridge;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.philips.lighting.model.*;

import com.philips.lighting.model.rule.PHRule;
import com.philips.lighting.model.sensor.PHSensor;
import lombok.RequiredArgsConstructor;
import no.cx.iot.facade.lightstate.InputSource;

@RequiredArgsConstructor
public class DummyBridge implements Bridge {

    @Override
    public PHBridgeResourcesCache getResourceCache() {
        return new PHBridgeResourcesCache() {
            @Override
            public Map<String, PHLight> getLights() {
                return null;
            }

            @Override
            public List<PHLight> getAllLights() {
                return null;
            }

            @Override
            public Map<String, PHGroup> getGroups() {
                return null;
            }

            @Override
            public List<PHGroup> getAllGroups() {
                return null;
            }

            @Override
            public List<PHScene> getAllScenes() {
                return null;
            }

            @Override
            public PHBridgeConfiguration getBridgeConfiguration() {
                return null;
            }

            @Override
            public Map<String, PHSchedule> getSchedules() {
                return null;
            }

            @Override
            public Map<String, PHSensor> getSensors() {
                return null;
            }

            @Override
            public Map<String, PHRule> getRules() {
                return null;
            }

            @Override
            public Map<String, PHScene> getScenes() {
                return null;
            }

            @Override
            public List<PHSchedule> getAllSchedules(boolean b) {
                return null;
            }

            @Override
            public List<PHSchedule> getAllTimers(boolean b) {
                return null;
            }

            @Override
            public List<PHSensor> getAllSensors() {
                return null;
            }

            @Override
            public List<PHRule> getAllRules() {
                return null;
            }
        };
    }

    @Override
    public List<PHLight> getAllLights() {
        PHLight phLight = new PHLight("a", "b", "c", "d");
        return Collections.singletonList(phLight);
    }

    @Override
    public void updateLightState(PHLight light, PHLightState lastKnownLightState) {

    }

    @Override
    public PHLightState getLastKnownLightState(PHLight light) {
        PHLightState lightState = new PHLightState();
        lightState.setReachable(true);
        return lightState;
    }

    @Override
    public InputSource getInputSource() {
        return InputSource.FAKE;
    }
}
