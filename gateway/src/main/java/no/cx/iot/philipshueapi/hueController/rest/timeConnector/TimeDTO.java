package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeDTO {
    private String timeRepresentation;

    public ZonedDateTime getDateTime() {
        return ZonedDateTime.parse(timeRepresentation);
    }
}
