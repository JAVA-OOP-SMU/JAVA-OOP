package weather.domain;

public class Weather {
    private String location; // 지역명
    private String measurementTime; // 측정일시
    private String pollutant; // 지수결정물질
    private String stationName; // 측정소명
    private String airQualityGrade; // 통합대기환경지수 등급
    private String pm10; // 미세먼지(PM10) 농도
    private String pm25; // 초미세먼지(PM2.5) 농도

    public Weather(String location, String measurementTime, String pollutant,
                   String stationName, String airQualityGrade, String pm10, String pm25) {
        this.location = location;
        this.measurementTime = measurementTime;
        this.pollutant = pollutant;
        this.stationName = stationName;
        this.airQualityGrade = airQualityGrade;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }

    @Override
    public String toString() {
        return String.format(
                "=== %s 대기질 정보 ===\n" +
                        "측정소: %s\n" + "측정시간: %s\n" +
                        "통합대기환경지수: %s\n" + "미세먼지(PM10): %sμg/m^3\n" +
                        "초미세먼지(PM2.5): %sμg/m^3\n" + "지수결정물질: %s",
                location, stationName, measurementTime, airQualityGrade, pm10, pm25, pollutant
        );
    }

    // Getter
    public String getAirQualityGrade() {
        return airQualityGrade;
    }

    public String getLocation() {
        return location;
    }

    public String getPm25() {
        return pm25;
    }

    public String getPm10() {
        return pm10;
    }
}