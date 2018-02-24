package no.iot.weatherservice.utils.general;

public class ExceptionWrapper {
    public static <T> T wrapExceptions(ThrowingSupplier<T> throwingSupplier) {
        return throwingSupplier.get();
    }
}
