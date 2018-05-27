package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

import java.util.concurrent.Callable;

public class ExceptionWrapper {
    public static <T> T wrapExceptions(Callable<T> throwingSupplier) {
        try {
            return throwingSupplier.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
