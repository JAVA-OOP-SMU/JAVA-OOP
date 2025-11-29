package location.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 서울 지역을 카테고리별로 관리하는 클래스
 */
public class LocationCategory {
    private static final Map<String, List<String>> CATEGORIES = new LinkedHashMap<>();

    static {
        // 관광특구 (7개)
        List<String> tourism = new ArrayList<>();
        tourism.add("강남 MICE 관광특구");
        tourism.add("동대문 관광특구");
        tourism.add("명동 관광특구");
        tourism.add("이태원 관광특구");
        tourism.add("잠실 관광특구");
        tourism.add("종로·청계 관광특구");
        tourism.add("홍대 관광특구");
        CATEGORIES.put("관광특구", tourism);

        // 고궁·문화유산 (5개)
        List<String> heritage = new ArrayList<>();
        heritage.add("경복궁");
        heritage.add("광화문·덕수궁");
        heritage.add("보신각");
        heritage.add("서울 암사동 유적");
        heritage.add("창덕궁·종묘");
        CATEGORIES.put("궁궐·문화유산", heritage);

        // 인구밀집지역 - 역 (42개)
        List<String> stations = new ArrayList<>();
        stations.add("가산디지털단지역");
        stations.add("강남역");
        stations.add("건대입구역");
        stations.add("고덕역");
        stations.add("고속터미널역");
        stations.add("교대역");
        stations.add("구로디지털단지역");
        stations.add("구로역");
        stations.add("군자역");
        stations.add("대림역");
        stations.add("동대문역");
        stations.add("뚝섬역");
        stations.add("미아사거리역");
        stations.add("발산역");
        stations.add("사당역");
        stations.add("삼각지역");
        stations.add("서울대입구역");
        stations.add("서울식물원·마곡나루역");
        stations.add("서울역");
        stations.add("선릉역");
        stations.add("성신여대입구역");
        stations.add("수유역");
        stations.add("신논현역·논현역");
        stations.add("신도림역");
        stations.add("신림역");
        stations.add("신촌·이대역");
        stations.add("양재역");
        stations.add("역삼역");
        stations.add("연신내역");
        stations.add("오목교역·목동운동장");
        stations.add("왕십리역");
        stations.add("용산역");
        stations.add("이태원역");
        stations.add("장지역");
        stations.add("장한평역");
        stations.add("천호역");
        stations.add("총신대입구(이수)역");
        stations.add("충정로역");
        stations.add("합정역");
        stations.add("혜화역");
        stations.add("홍대입구역(2호선)");
        stations.add("회기역");
        CATEGORIES.put("지하철역", stations);

        // 발달상권 (23개)
        List<String> commercial = new ArrayList<>();
        commercial.add("가락시장");
        commercial.add("가로수길");
        commercial.add("광장(전통)시장");
        commercial.add("김포공항");
        commercial.add("노량진");
        commercial.add("덕수궁길·정동길");
        commercial.add("북촌한옥마을");
        commercial.add("서촌");
        commercial.add("성수카페거리");
        commercial.add("쌍문역");
        commercial.add("압구정로데오거리");
        commercial.add("여의도");
        commercial.add("연남동");
        commercial.add("영등포 타임스퀘어");
        commercial.add("용리단길");
        commercial.add("이태원 앤틱가구거리");
        commercial.add("인사동");
        commercial.add("창동 신경제 중심지");
        commercial.add("청담동 명품거리");
        commercial.add("청량리 제기동 일대 전통시장");
        commercial.add("해방촌·경리단길");
        commercial.add("DDP(동대문디자인플라자)");
        commercial.add("DMC(디지털미디어시티)");
        CATEGORIES.put("상권·시장", commercial);

        // 공원 (33개)
        List<String> parks = new ArrayList<>();
        parks.add("강서한강공원");
        parks.add("고척돔");
        parks.add("광나루한강공원");
        parks.add("광화문광장");
        parks.add("국립중앙박물관·용산가족공원");
        parks.add("난지한강공원");
        parks.add("남산공원");
        parks.add("노들섬");
        parks.add("뚝섬한강공원");
        parks.add("망원한강공원");
        parks.add("반포한강공원");
        parks.add("북서울꿈의숲");
        parks.add("서리풀공원·몽마르뜨공원");
        parks.add("서울광장");
        parks.add("서울대공원");
        parks.add("서울숲공원");
        parks.add("아차산");
        parks.add("양화한강공원");
        parks.add("어린이대공원");
        parks.add("여의도한강공원");
        parks.add("월드컵공원");
        parks.add("응봉산");
        parks.add("이촌한강공원");
        parks.add("잠실종합운동장");
        parks.add("잠실한강공원");
        parks.add("잠원한강공원");
        parks.add("청계산");
        parks.add("청와대");
        parks.add("보라매공원");
        parks.add("서대문독립공원");
        parks.add("안양천");
        parks.add("여의서로");
        parks.add("올림픽공원");
        CATEGORIES.put("공원", parks);

        // 기타 지역 (10개)
        List<String> others = new ArrayList<>();
        others.add("북창동 먹자골목");
        others.add("남대문시장");
        others.add("익선동");
        others.add("신정네거리역");
        others.add("잠실새내역");
        others.add("잠실역");
        others.add("잠실롯데타워 일대");
        others.add("송리단길·호수단길");
        others.add("신촌 스타광장");
        others.add("홍제폭포");
        CATEGORIES.put("기타", others);
    }

    /**
     * 모든 카테고리 반환
     */
    public static Map<String, List<String>> getAllCategories() {
        return CATEGORIES;
    }

    /**
     * 특정 카테고리의 장소 목록 반환
     */
    public static List<String> getLocationsByCategory(String category) {
        return CATEGORIES.getOrDefault(category, new ArrayList<>());
    }

    /**
     * 모든 장소를 리스트로 반환
     */
    public static List<String> getAllLocations() {
        List<String> allLocations = new ArrayList<>();
        CATEGORIES.values().forEach(allLocations::addAll);
        return allLocations;
    }

    /**
     * 장소명 검색 (부분 일치)
     */
    public static List<String> searchLocations(String keyword) {
        List<String> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (List<String> locations : CATEGORIES.values()) {
            for (String location : locations) {
                if (location.toLowerCase().contains(lowerKeyword)) {
                    results.add(location);
                }
            }
        }

        return results;
    }

    /**
     * 카테고리 이름 목록 반환
     */
    public static List<String> getCategoryNames() {
        return new ArrayList<>(CATEGORIES.keySet());
    }
}
