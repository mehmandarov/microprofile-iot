package no.cx.iot.facade.sdk;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

class SDKFacade {

    private PHHueSDK sdk;

    SDKFacade() {
        sdk = PHHueSDK.getInstance();
    }

    void connect(PHAccessPoint accessPoint) {
        sdk.connect(accessPoint);
    }

    PHBridge getSelectedBridge() {
        return sdk.getSelectedBridge();
    }

    PHNotificationManager getNotificationManager() {
        return sdk.getNotificationManager();
    }

    void setSelectedBridge(PHBridge bridge) {
        sdk.setSelectedBridge(bridge);
    }

    void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        sdk.startPushlinkAuthentication(accessPoint);
    }

    Object getSDKService(byte searchBridge) {
        return sdk.getSDKService(searchBridge);
    }

    void enableHeartbeat(PHBridge bridge, int hbInterval) {
        sdk.enableHeartbeat(bridge, hbInterval);
    }
}
