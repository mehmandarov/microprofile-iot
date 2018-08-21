package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

import java.util.concurrent.Callable;

public class ExceptionWrapper {
    public static <T> T wrapExceptions(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
