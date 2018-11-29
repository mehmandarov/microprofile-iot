package no.cx.iot.gateway.time;

import java.time.ZonedDateTime;

public class TimeDTO {
    private String timeRepresentation;

    public ZonedDateTime getDateTime() {
        return ZonedDateTime.parse(timeRepresentation);
    }

    public String getTimeRepresentation() {
        return timeRepresentation;
    }

    public void setTimeRepresentation(String timeRepresentation) {
        this.timeRepresentation = timeRepresentation;
    }
}
