package no.cx.iot.weatherservice.cache;

import java.time.LocalDateTime;

class TimeDTO {
    private final String timeRepresentation;

    TimeDTO(String timeRepresentation) {
        this.timeRepresentation = timeRepresentation;
    }

    TimeDTO(LocalDateTime localDateTime) {
        timeRepresentation = localDateTime.toString();
    }

    public String getTimeRepresentation() {
        return timeRepresentation;
    }
}

