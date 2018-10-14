package no.cx.iot.weatherservice.weather.yr;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class YrURLProvider {
    @Inject
    @ConfigProperty(name = "yr_URL_start")
    private String yrURLStart;

    @Inject
    @ConfigProperty(name = "yr_URL_end")
    private String yrURLEnd;

    @Inject
    @ConfigProperty(name = "country")
    private String country;

    @Inject
    @ConfigProperty(name = "yr_city_part")
    private String yrCityPart;

    String getURL() {
        return yrURLStart + country + "/" + yrCityPart + yrURLEnd;
    }
}
