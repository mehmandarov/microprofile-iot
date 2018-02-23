package no.cx.iot.philipshueapi.hueAPI.sdk;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SDKFacade {

    private PHHueSDK sdk;

    public SDKFacade() {
        sdk = PHHueSDK.getInstance();
    }

    public Object getSDKService(byte searchBridge) {
        return sdk.getSDKService(searchBridge);
    }

    public void connect(PHAccessPoint accessPoint) {
        sdk.connect(accessPoint);
    }

    public PHBridge getSelectedBridge() {
        return sdk.getSelectedBridge();
    }

    public PHNotificationManager getNotificationManager() {
        return sdk.getNotificationManager();
    }

    public void setSelectedBridge(PHBridge bridge) {
        sdk.setSelectedBridge(bridge);
    }

    public void enableHeartbeat(PHBridge bridge, int hbInterval) {
        sdk.enableHeartbeat(bridge, hbInterval);
    }

    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        sdk.startPushlinkAuthentication(accessPoint);
    }
}
