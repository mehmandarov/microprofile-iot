package no.iot.weatherservice.supplier;

import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.iot.weatherservice.rest.WeatherDTO;
import no.iot.weatherservice.weather.yr.YrInputProvider;

@ApplicationScoped
public class LocalWeatherSupplier implements Supplier<WeatherDTO> {

    @Inject
    private YrInputProvider yrInputProvider;

    @Override
    public WeatherDTO get() {
        WeatherDTO weatherDTO = createWeatherDTO();
        validate(weatherDTO);
        return weatherDTO;
    }

    private WeatherDTO createWeatherDTO() {
        return new WeatherDTO(yrInputProvider.getTemperature());
    }

    private void validate(WeatherDTO weather) {
        Double.parseDouble(weather.getTemperature());
    }
}
