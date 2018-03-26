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
                if (observations.length > 1) {
                    if (!observations[0].startsWith("ERROR")) {
                        return getTemperature(observations[1]);
                    }
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

    private String getCountry(String xml) {
        return xml.split("country>", 3)[1].replace("</", "");
    }

    private String getTemperature(String observation1) {
        String[] observation = observation1.split("<temperature unit=\"celsius\" value=", 2);
        String[] onlyTemperature = observation[1].split("time", 2);
        String[] x = onlyTemperature[0].split("/>", 2);
        return x[0].
                replaceAll("\"", "")
                .trim();
    }
}
