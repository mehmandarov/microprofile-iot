package no.iot.weatherservice.weather;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class XMLToTemperatureConverter {

    @Inject
    private Logger logger;

    // Yes, this is obscure. But it works. At least atm. Should be improved.
    public String convert(String xml) {
        try {
            String country = getCountry(xml);

            if ("Norway".equals(country)) {
                String[] observations = xml.split("<observations>", 2);
                if (isProperObservation(observations)) {
                    return getTemperature(observations[1]);
                }
            } else if (xml.contains("temperature")) {
                String temperature = getTemperature(xml);
                if (!temperature.contains("ERROR")) {
                    return temperature;
                }
            }
        }
        catch (RuntimeException e) {
            logger.warning(e.getMessage());
        }
        return "ERROR";
    }

    private boolean isProperObservation(String[] observations) {
        return observations.length > 1 && !observations[0].startsWith("ERROR");
    }

    private String getCountry(String xml) {
        String[] splitOnCountry = xml
                .split("country>", 3);
        if (splitOnCountry.length == 0) {
            return "ERROR";
        }
        return splitOnCountry[1]
                .replace("</", "");
    }

    private String getTemperature(String xml) {
        String observation = xml.split(getPatternBeforeTemperature(), 2)[1];
        String onlyTemperature = observation.split("time", 2)[0];

        return onlyTemperature.split("/>", 2)[0]
                .replaceAll("\"", "")
                .trim();
    }

    private String getPatternBeforeTemperature() {
        return "<temperature unit=\"celsius\" value=";
    }
}
