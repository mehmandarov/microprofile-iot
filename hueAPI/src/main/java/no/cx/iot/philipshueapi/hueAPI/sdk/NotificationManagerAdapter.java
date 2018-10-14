package no.cx.iot.philipshueapi.hueAPI.sdk;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.philipshueapi.hueAPI.logic.Listener;

@ApplicationScoped
@SuppressWarnings("unused")
public class NotificationManagerAdapter {

    @Inject
    private SDKFacade sdk;

    @Inject
    private Listener listener;

    @PreDestroy
    public void tearDown() {
        unregisterSDKListener();
    }

    private void unregisterSDKListener() {
        if (sdk.useRealBridge()) sdk.getNotificationManager().unregisterSDKListener(listener);
    }

    public void registerSDKListener() {
        if (sdk.useRealBridge()) sdk.getNotificationManager().registerSDKListener(listener);
    }
}
