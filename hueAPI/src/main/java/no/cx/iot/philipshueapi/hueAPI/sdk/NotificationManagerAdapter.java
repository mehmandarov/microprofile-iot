package no.cx.iot.philipshueapi.hueAPI.sdk;

import no.cx.iot.philipshueapi.hueAPI.logic.Listener;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
        sdk.getNotificationManager().unregisterSDKListener(listener);
    }

    public void registerSDKListener() {
        sdk.getNotificationManager().registerSDKListener(listener);
    }
}
