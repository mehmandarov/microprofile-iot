package no.iot.weatherservice.utils.general;

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
