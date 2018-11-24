package no.cx.iot.gateway;

public enum InputSource {
    WEATHER(2),
    TIME(1),
    COMPUTED(0),
    LIGHT(-1),
    FAKE(-2),
    ERROR(-3);

    private final Integer priority;

    InputSource(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}
