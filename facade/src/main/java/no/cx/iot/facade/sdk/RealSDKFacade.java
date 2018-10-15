package no.cx.iot.facade.sdk;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

class RealSDKFacade implements SDKFacade {

    private PHHueSDK sdk;

    RealSDKFacade() {
        sdk = PHHueSDK.getInstance();
    }

    @Override
    public void connect(PHAccessPoint accessPoint) {
        sdk.connect(accessPoint);
    }

    @Override
    public PHBridge getSelectedBridge() {
        return sdk.getSelectedBridge();
    }

    @Override
    public PHNotificationManager getNotificationManager() {
        return sdk.getNotificationManager();
    }

    @Override
    public void setSelectedBridge(PHBridge bridge) {
        sdk.setSelectedBridge(bridge);
    }

    @Override
    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        sdk.startPushlinkAuthentication(accessPoint);
    }

    @Override
    public Object getSDKService(byte searchBridge) {
        return sdk.getSDKService(searchBridge);
    }

    @Override
    public void enableHeartbeat(PHBridge bridge, int hbInterval) {
        sdk.enableHeartbeat(bridge, hbInterval);
    }
}
