package no.cx.iot.facade.logic;

import no.cx.iot.facade.HueProperties;
import no.cx.iot.facade.sdk.NotificationManagerAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@SuppressWarnings("unused")
class SetupController {

    @Inject
    private HueProperties hueProperties;

    @Inject
    private BridgeConnector bridgeConnector;

    @Inject
    private NotificationManagerAdapter notificationManagerAdapter;

    public void setup() {
        bridgeConnector.connectToLastKnownAccessPoint();
        notificationManagerAdapter.registerSDKListener();
        bridgeConnector.findBridges();
    }
}
