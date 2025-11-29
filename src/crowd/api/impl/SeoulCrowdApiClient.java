package crowd.api.impl;

import crowd.api.CrowdApiClient;
import crowd.domain.CrowdInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.XmlParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;

/**
 * 서울시 실시간 유동 인구 API 클라이언트 구현체
 */
public class SeoulCrowdApiClient implements CrowdApiClient {

    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";
    private final String apiKey;

    public SeoulCrowdApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public CrowdInfo fetchCrowdInfo(String location) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("citydata_ppltn", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("5", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(location, "UTF-8"));

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("SeoulRtd.citydata_ppltn");
        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);

            return new CrowdInfo(
                    XmlParser.getTagValue("AREA_NM", element),
                    XmlParser.getTagValue("AREA_CD", element),
                    XmlParser.getTagValue("AREA_CONGEST_LVL", element),
                    XmlParser.getTagValue("AREA_CONGEST_MSG", element),
                    XmlParser.getTagValue("AREA_PPLTN_MIN", element),
                    XmlParser.getTagValue("AREA_PPLTN_MAX", element),
                    XmlParser.getTagValue("MALE_PPLTN_RATE", element),
                    XmlParser.getTagValue("FEMALE_PPLTN_RATE", element),
                    XmlParser.getTagValue("PPLTN_TIME", element)
            );
        }

        throw new Exception("유동 인구 데이터를 찾을 수 없습니다: " + location);
    }

    @Override
    public boolean checkConnection() {
        // API 이용 상태 확인
        try {
            fetchCrowdInfo("광화문·덕수궁"); // 광화문·덕수궁을 기본 값으로 사용하기
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
