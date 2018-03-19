package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TimeDTO {
    private String timeRepresentation;

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(timeRepresentation);
    }
}
