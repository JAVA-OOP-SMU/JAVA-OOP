package weather.api;

import weather.domain.Weather;

import java.util.List;

public interface WeatherApiClient {
    /**
     * 특정 지역의 대기질 정보 조회
     */
    Weather fetchWeather(String location) throws Exception;

    /**
     * 모든 지역의 대기질 정보 조회
     */
    List<Weather> fetchAllWeather() throws Exception;

    /**
     * API 연결 상태 확인
     */
    boolean checkConnection();
}