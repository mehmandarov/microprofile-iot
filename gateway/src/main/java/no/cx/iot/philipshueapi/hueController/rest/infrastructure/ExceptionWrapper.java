package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

public class ExceptionWrapper {
    public static <T> T wrapExceptions(ThrowingSupplier<T> throwingSupplier) {
        return throwingSupplier.get();
    }
}
