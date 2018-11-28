package no.cx.iot.facade.logic;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.awaitility.Duration;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHBridge;

import no.cx.iot.facade.HueProperties;
import no.cx.iot.facade.sdk.Bridge;
import no.cx.iot.facade.sdk.NotificationManagerAdapter;
import no.cx.iot.facade.sdk.SDKAdapter;

import static org.awaitility.Awaitility.with;

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


    private void waitUntilBridgeIsSelected() {
        with()
                .conditionEvaluationListener(l -> System.out.println("Waiting for bridge selection"))
                .await()
                .pollInterval(Duration.FIVE_HUNDRED_MILLISECONDS)
                .atMost(30, TimeUnit.SECONDS)
                .until(sdk::isBridgeSelected);
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
