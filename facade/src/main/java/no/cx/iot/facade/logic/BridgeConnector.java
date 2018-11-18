package no.cx.iot.facade.logic;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHBridge;

import no.cx.iot.facade.HueProperties;
import no.cx.iot.facade.sdk.Bridge;
import no.cx.iot.facade.sdk.NotificationManagerAdapter;
import no.cx.iot.facade.sdk.SDKAdapter;

@ApplicationScoped
class BridgeConnector {

    @Inject
    private Logger logger;

    @Inject
    private SDKAdapter sdk;

    @Inject
    private HueProperties hueProperties;

    @Inject
    private NotificationManagerAdapter notificationManagerAdapter;

    private void connectToLastKnownAccessPoint() {
        Optional<String> username = Optional.ofNullable(hueProperties.getUsername());
        Optional<String> lastIpAddress = Optional.ofNullable(hueProperties.getLastConnectedIP());

        if (!username.isPresent() || !lastIpAddress.isPresent()) {
            return;
        }

        createAndConnectToAccessPoint(username.get(), lastIpAddress.get());
    }

    private void createAndConnectToAccessPoint(String username, String lastIpAddress) {
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(lastIpAddress);
        accessPoint.setUsername(username);
        connect(accessPoint);
    }

    private void findBridges() {
        sdk.search(true, true);
    }

    private void connect(PHAccessPoint accessPoint) {
        Optional<String> connectedIPAddress = sdk.getConnectedIPAddress();
        if (!connectedIPAddress.isPresent()) {
            sdk.connect(accessPoint);
        }
    }

    void onBridgeConnected(PHBridge bridge, String username) {
        sdk.setSelectedBridge(bridge);
        sdk.enableHeartbeatHBInterval();
        hueProperties.storeConnectionData(username, getLastIpAddress(bridge));
    }

    private String getLastIpAddress(PHBridge bridge) {
        return bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
    }

    void connectToArbitraryAccessPoint(List<PHAccessPoint> list) {
        list.stream()
                .limit(1)
                .forEach(this::connect);
    }

    void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        sdk.startPushlinkAuthentication(accessPoint);
    }


    private void waitUntilBridgeIsSelected() { //TODO: Replace this with using awaitility
        int counter = 0;
        while (!sdk.isBridgeSelected()) {
            try {
                logger.warning("Waiting for bridge selection");
                Thread.sleep(400);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (counter++ > 50) {
                throw new RuntimeException("Waited too long for bridgeselection");
            }
        }
    }

    Bridge getBridge() {
        return sdk.getBridge();
    }

    void setup() {
        connectToLastKnownAccessPoint();
        notificationManagerAdapter.registerSDKListener();
        findBridges();
        waitUntilBridgeIsSelected();
    }
}
