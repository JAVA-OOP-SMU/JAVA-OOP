package weather.api.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.XmlParser;
import weather.api.WeatherApiClient;
import weather.domain.Weather;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 서울시 실시간 대기질 API 클라이언트 구현체
 */
public class SeoulWeatherApiClient implements WeatherApiClient {

    private static final String BASE_URL = "http://openAPI.seoul.go.kr:8088";
    private final String apiKey;

    // 자치구명 -> 측정소 행정코드 매핑
    private static final Map<String, String> DISTRICT_CODE_MAP = new HashMap<>();

    static {
        DISTRICT_CODE_MAP.put("종로구", "111123");
        DISTRICT_CODE_MAP.put("중구", "111121");
        DISTRICT_CODE_MAP.put("용산구", "111131");
        DISTRICT_CODE_MAP.put("성동구", "111142");
        DISTRICT_CODE_MAP.put("광진구", "111141");
        DISTRICT_CODE_MAP.put("동대문구", "111152");
        DISTRICT_CODE_MAP.put("중랑구", "111151");
        DISTRICT_CODE_MAP.put("성북구", "111161");
        DISTRICT_CODE_MAP.put("강북구", "111291");
        DISTRICT_CODE_MAP.put("도봉구", "111171");
        DISTRICT_CODE_MAP.put("노원구", "111131");
        DISTRICT_CODE_MAP.put("은평구", "111181");
        DISTRICT_CODE_MAP.put("서대문구", "111191");
        DISTRICT_CODE_MAP.put("마포구", "111201");
        DISTRICT_CODE_MAP.put("양천구", "111301");
        DISTRICT_CODE_MAP.put("강서구", "111212");
        DISTRICT_CODE_MAP.put("구로구", "111221");
        DISTRICT_CODE_MAP.put("금천구", "111281");
        DISTRICT_CODE_MAP.put("영등포구", "111231");
        DISTRICT_CODE_MAP.put("동작구", "111241");
        DISTRICT_CODE_MAP.put("관악구", "111251");
        DISTRICT_CODE_MAP.put("서초구", "111262");
        DISTRICT_CODE_MAP.put("강남구", "111261");
        DISTRICT_CODE_MAP.put("송파구", "111273");
        DISTRICT_CODE_MAP.put("강동구", "111274");
    }

    public SeoulWeatherApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Weather fetchWeather(String location) throws Exception {
        // 입력된 지역이 존재여부 확인
        String districtCode = DISTRICT_CODE_MAP.get(location);
        if (districtCode == null) {
            throw new Exception("지원하지 않는 지역입니다: " + location);
        }

        // URL 생성하기
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("/").append(URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("ListAirQualityByDistrictService", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("5", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode(districtCode, "UTF-8"));

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // HTTP GET 요청이후에 XML을 받아서 Document 객체로 바꾸기
        Document doc = dBuilder.parse(urlBuilder.toString());
        doc.getDocumentElement().normalize();

        // 에러 체크
        validateApiResponse(doc);

        // XML에서 row 태그들을 찾기
        NodeList rowList = doc.getElementsByTagName("row");
        if (rowList.getLength() > 0) {
            Element element = (Element) rowList.item(0);
            return createWeatherInfo(location, element);
        }

        throw new Exception("날씨 데이터를 찾을 수 없습니다: " + location);
    }

    @Override
    public List<Weather> fetchAllWeather() throws Exception {
        // URL을 생성
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("/").append(URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("ListAirQualityByDistrictService", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("25", "UTF-8"));

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // HTTP GET 요청이후에 XML을 받아서 Document 객체로 바꾸기
        Document doc = dBuilder.parse(urlBuilder.toString());
        doc.getDocumentElement().normalize();

        // 에러 체크
        validateApiResponse(doc);

        // 자치구의 날씨 정보를 저장할 리스트
        List<Weather> weatherList = new ArrayList<>();

        // XML에서 row 태그들을 찾기
        NodeList rowList = doc.getElementsByTagName("row");
        for (int i = 0; i < rowList.getLength(); i++) {
            Element element = (Element) rowList.item(i);
            String districtName = XmlParser.getTagValue("MSRSTN_NM", element);
            weatherList.add(createWeatherInfo(districtName, element));
        }

        return weatherList;
    }

    @Override
    public boolean checkConnection() {
        try {
            fetchWeather("종로구");
            return true;
        } catch (Exception e) {
            System.err.println("연결 확인 실패: " + e.getMessage());
            return false;
        }
    }

    // API 응답 유효한지 검증하는 메서드
    private void validateApiResponse(Document doc) throws Exception {
        NodeList codeList = doc.getElementsByTagName("CODE");
        if (codeList.getLength() > 0) {
            String code = codeList.item(0).getTextContent();
            if (!"INFO-000".equals(code)) {
                NodeList messageList = doc.getElementsByTagName("MESSAGE");
                String message = messageList.getLength() > 0 ?
                        messageList.item(0).getTextContent() : "알 수 없는 오류";
                throw new Exception("API 오류 [" + code + "]: " + message);
            }
        }
    }

    // XML Element로부터 WeatherInfo 객체 생성하는 메서드
    private Weather createWeatherInfo(String location, Element element) {
        return new Weather(
                location,
                XmlParser.getTagValue("MSRMT_YMD", element), // 측정일시
                XmlParser.getTagValue("SAREA_NM", element), // 지수결정물질
                XmlParser.getTagValue("MSRSTN_NM", element), // 측정소명
                XmlParser.getTagValue("CAI_GRD", element), // 통합대기환경지수 등급
                XmlParser.getTagValue("PM", element), // 미세먼지(PM10)
                XmlParser.getTagValue("FPM", element) // 초미세먼지(PM2.5)
        );
    }
}