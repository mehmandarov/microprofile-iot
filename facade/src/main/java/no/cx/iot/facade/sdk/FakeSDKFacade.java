package no.cx.iot.facade.sdk;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;

public class FakeSDKFacade implements SDKFacade {
    @Override
    public void connect(PHAccessPoint accessPoint) {

    }

    @Override
    public PHBridge getSelectedBridge() {
        return null;
    }

    @Override
    public PHNotificationManager getNotificationManager() {
        return new PHNotificationManager() {
            @Override
            public void registerSDKListener(PHSDKListener phsdkListener) {

            }

            @Override
            public void unregisterSDKListener(PHSDKListener phsdkListener) {

            }

            @Override
            public void cancelSearchNotification() {

            }
        };
    }

    @Override
    public void setSelectedBridge(PHBridge bridge) {

    }

    @Override
    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {

    }

    @Override
    public Object getSDKService(byte searchBridge) {
        return new PHBridgeSearchManager() {
            @Override
            public void search(boolean b, boolean b1) {

            }

            @Override
            public void search(boolean b, boolean b1, boolean b2) {

            }

            @Override
            public void upnpSearch() {

            }

            @Override
            public void portalSearch() {

            }

            @Override
            public void ipAddressSearch() {

            }

            @Override
            public void setPortalAddress(String s) {

            }

            @Override
            public String getPortalAddress() {
                return null;
            }
        };
    }

    @Override
    public void enableHeartbeat(PHBridge bridge, int hbInterval) {

    }
}
