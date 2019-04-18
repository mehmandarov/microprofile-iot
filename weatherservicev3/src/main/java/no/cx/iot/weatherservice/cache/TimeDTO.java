package no.cx.iot.weatherservice.cache;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TimeDTO implements Serializable {
    private String timeRepresentation;

    public TimeDTO() {

    }

    TimeDTO(String timeRepresentation) {
        this.timeRepresentation = timeRepresentation;
    }

    TimeDTO(LocalDateTime localDateTime) {
        timeRepresentation = localDateTime.toString();
    }

    public String getTimeRepresentation() {
        return timeRepresentation;
    }

    public void setTimeRepresentation(String timeRepresentation) {
        this.timeRepresentation = timeRepresentation;
    }
}

