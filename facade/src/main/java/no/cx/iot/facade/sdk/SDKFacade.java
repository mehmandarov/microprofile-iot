package no.cx.iot.facade.sdk;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

public interface SDKFacade {
    void connect(PHAccessPoint accessPoint);

    PHBridge getSelectedBridge();

    PHNotificationManager getNotificationManager();

    void setSelectedBridge(PHBridge bridge);

    void startPushlinkAuthentication(PHAccessPoint accessPoint);

    Object getSDKService(byte searchBridge);

    void enableHeartbeat(PHBridge bridge, int hbInterval);
}
