package no.iot.weatherservice.supplier;

import no.iot.weatherservice.rest.WeatherDTO;
import no.iot.weatherservice.weather.yr.YrInputProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Supplier;

@ApplicationScoped
public class LocalWeatherSupplier implements Supplier<WeatherDTO> {

    @Inject
    private YrInputProvider yrInputProvider;

    @Override
    public WeatherDTO get() {
        return new WeatherDTO(yrInputProvider.getTemperature());
    }
}
