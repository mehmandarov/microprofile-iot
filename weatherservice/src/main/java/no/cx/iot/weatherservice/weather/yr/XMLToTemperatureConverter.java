package no.cx.iot.weatherservice.weather.yr;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eaxy.Document;
import org.eaxy.Xml;

@RequestScoped
public class XMLToTemperatureConverter {

    @Inject
    private Logger logger;

    public String convert(String xmlAsString) {
        try {
            Document xml = Xml.xml(xmlAsString);
            String country = getCountry(xml);
            if ("Norway".equals(country)) {
                return xml.find("observations", "weatherstation", "temperature").first().val();
            }
            else {
                String val = xml.find("forecast", "tabular", "time", "temperature").first().val();
                if (!val.contains("ERROR")) {
                    return val;
                }
            }
        }
        catch (RuntimeException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return "ERROR";
    }

    private String getCountry(Document xml) {
        return String.valueOf(xml.find("location", "country").first().children().iterator().next().text());
    }
}
