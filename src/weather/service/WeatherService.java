package weather.service;

import weather.api.WeatherApiClient;
import weather.domain.Weather;

import java.util.List;

/**
 * 날씨 서비스
 */
public class WeatherService {
    private final WeatherApiClient weatherApiClient;
    
    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }
    
    public Weather getWeather(String location) throws Exception {
        return weatherApiClient.fetchWeather(location);
    }
    
    public List<Weather> getAllWeather() throws Exception {
        return weatherApiClient.fetchAllWeather();
    }

    public boolean isApiAvailable() {
        return weatherApiClient.checkConnection();
    }
}
