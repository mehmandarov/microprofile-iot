package no.cx.iot.gateway.infrastructure;

import java.util.concurrent.Callable;

public class ExceptionWrapper {
    public static <T> T wrapExceptions(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
