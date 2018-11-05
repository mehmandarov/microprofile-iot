package no.cx.iot.weatherservice.cache;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class TimeDTO {
    private final String timeRepresentation;

    TimeDTO(LocalDateTime localDateTime) {
        timeRepresentation = localDateTime.toString();
    }
}

