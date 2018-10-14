package no.cx.iot.facade.sdk;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.cx.iot.facade.logic.Listener;

@ApplicationScoped
@SuppressWarnings("unused")
public class NotificationManagerAdapter {

    @Inject
    private SDKAdapter sdk;

    @Inject
    private Listener listener;

    @PreDestroy
    public void tearDown() {
        unregisterSDKListener();
    }

    private void unregisterSDKListener() {
        sdk.unregisterSDKListener(listener);
    }

    public void registerSDKListener() {
        sdk.registerSDKListener(listener);
    }
}
