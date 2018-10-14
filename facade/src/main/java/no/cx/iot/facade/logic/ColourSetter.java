package no.cx.iot.facade.logic;

import javax.enterprise.context.ApplicationScoped;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.bridge.Bridge;

@ApplicationScoped
public class ColourSetter {

    public void setColorOnLight(Bridge bridge, int color, PHLight light, PHLightState lastKnownLightState) {
        lastKnownLightState.setColorMode(PHLight.PHLightColorMode.COLORMODE_XY);
        float[] xy = PHUtilities.calculateXY(color, PHLight.PHLightColorMode.COLORMODE_XY.getValue());
        lastKnownLightState.setX(xy[0], true);
        lastKnownLightState.setY(xy[1], true);
        bridge.updateLightState(light, lastKnownLightState);
    }
}
