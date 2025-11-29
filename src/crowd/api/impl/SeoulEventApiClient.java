package crowd.api.impl;

import crowd.api.EventApiClient;
import crowd.domain.EventInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.XmlParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 서울시 문화행사 정보 API 클라이언트 구현체
 */
public class SeoulEventApiClient implements EventApiClient {

    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";
    private static final String SERVICE_NAME = "culturalEventInfo";
    private final String apiKey;

    public SeoulEventApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<EventInfo> fetchTodayEvents(String location) throws Exception {
        // 오늘 날짜를 YYYY-MM-DD 형식으로 가져오기
        LocalDate today = LocalDate.now();
        String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // API URL 생성하기
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("/").append(URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode(SERVICE_NAME, "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/").append(URLEncoder.encode("1000", "UTF-8"));

        // XML 파싱하기
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // HTTP GET 요청 후 XML을 Document 객체로 변환하기
        Document doc = dBuilder.parse(urlBuilder.toString());
        doc.getDocumentElement().normalize();

        // 에러 체크하기
        validateApiResponse(doc);

        List<EventInfo> eventList = new ArrayList<>();

        // XML에서 row 태그들을 찾기
        NodeList rowList = doc.getElementsByTagName("row");
        for (int i = 0; i < rowList.getLength(); i++) {
            Element element = (Element) rowList.item(i);

            // 날짜 필터링: 오늘 날짜에 진행 중인 행사만 포함하기
            String startDate = XmlParser.getTagValue("STRTDATE", element);
            String endDate = XmlParser.getTagValue("END_DATE", element);

            if (isEventOngoing(todayStr, startDate, endDate)) {
                // 지역 필터링
                if (location == null || location.isEmpty()) {
                    eventList.add(createEventInfo(element));
                } else {
                    String guName = XmlParser.getTagValue("GUNAME", element);
                    if (guName.contains(location)) {
                        eventList.add(createEventInfo(element));
                    }
                }
            }
        }

        return eventList;
    }

    @Override
    public boolean checkConnection() {
        // API 연결 상태 확인
        try {
            fetchTodayEvents(null);
            return true;
        } catch (Exception e) {
            System.err.println("연결 확인 실패: " + e.getMessage());
            return false;
        }
    }

    /**
     * API 응답이 유효한지 검증하는 메서드
     */
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

    /**
     * XML Element로부터 EventInfo 객체 생성하는 메서드
     */
    private EventInfo createEventInfo(Element element) {
        return new EventInfo(
                XmlParser.getTagValue("CODENAME", element),
                XmlParser.getTagValue("GUNAME", element),
                XmlParser.getTagValue("TITLE", element),
                XmlParser.getTagValue("DATE", element),
                XmlParser.getTagValue("PLACE", element),
                XmlParser.getTagValue("ORG_NAME", element),
                XmlParser.getTagValue("USE_TRGT", element),
                XmlParser.getTagValue("USE_FEE", element),
                XmlParser.getTagValue("PROGRAM", element),
                XmlParser.getTagValue("RGSTDATE", element),
                XmlParser.getTagValue("TICKET", element),
                XmlParser.getTagValue("IS_FREE", element),
                XmlParser.getTagValue("HMPG_ADDR", element),
                XmlParser.getTagValue("PRO_TIME", element)
        );
    }

    // 오늘 날짜에 행사가 진행 중인지 확인하는 메서드
    private boolean isEventOngoing(String today, String startDate, String endDate) {
        try {
            // 날짜 부분만 추출 (YYYY-MM-DD)
            String start = startDate.split(" ")[0];
            String end = endDate.split(" ")[0];

            // 날짜 범위 확인
            return today.compareTo(start) >= 0 && today.compareTo(end) <= 0;
        } catch (Exception e) {
            // 날짜 형식이 올바르지 않으면 false 반환하기
            return false;
        }
    }
}
