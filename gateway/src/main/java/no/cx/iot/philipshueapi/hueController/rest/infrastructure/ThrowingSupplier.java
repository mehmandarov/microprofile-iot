package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

@FunctionalInterface
public interface ThrowingSupplier<T> {

    T toOverride() throws Exception;

    default T get() {
        try {
            return toOverride();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
