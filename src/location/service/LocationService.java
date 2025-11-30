package location.service;

import location.domain.LocationCategory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 지역 검색 및 필터링 서비스
 */
public class LocationService {

    // 서울시 자치구별 주요 핫스팟 매핑
    private static final Map<String, List<String>> DISTRICT_LOCATIONS = new LinkedHashMap<>(); // 자치구, 해당 자치구에 해당 되는 장소 리스트

    // 서울 데이터 센터 API 엑셀 파일 분류 기준 적용
    static {
        // 강남구
        DISTRICT_LOCATIONS.put("강남구", List.of(
                "강남 MICE 관광특구", "강남역", "신논현역·논현역", "역삼역",
                "선릉역", "압구정로데오거리", "청담동 명품거리", "가로수길"
        ));

        // 종로구
        DISTRICT_LOCATIONS.put("종로구", List.of(
                "종로·청계 관광특구", "광화문·덕수궁", "경복궁", "혜화역",
                "인사동", "북촌한옥마을", "익선동", "광화문광장", "청와대"
        ));

        // 중구
        DISTRICT_LOCATIONS.put("중구", List.of(
                "명동 관광특구", "서울역", "남대문시장", "광장(전통)시장",
                "덕수궁길·정동길", "서촌", "북창동 먹자골목", "서울광장", "충정로역"
        ));

        // 동대문구
        DISTRICT_LOCATIONS.put("동대문구", List.of(
                "동대문 관광특구", "동대문역", "청량리 제기동 일대 전통시장",
                "DDP(동대문디자인플라자)", "회기역", "장한평역"
        ));

        // 강동구
        DISTRICT_LOCATIONS.put("강동구", List.of(
                "천호역", "고덕역", "광나루한강공원", "서울 암사동 유적"
        ));

        // 송파구
        DISTRICT_LOCATIONS.put("송파구", List.of(
                "잠실 관광특구", "잠실역", "잠실새내역", "잠실종합운동장",
                "잠실한강공원", "올림픽공원", "잠실롯데타워 일대",
                "송리단길·호수단길", "장지역", "가락시장"
        ));

        // 강서구
        DISTRICT_LOCATIONS.put("강서구", List.of(
                "강서한강공원", "김포공항", "발산역", "서울식물원·마곡나루역"
        ));

        // 영등포구
        DISTRICT_LOCATIONS.put("영등포구", List.of(
                "여의도", "여의도한강공원", "영등포 타임스퀘어", "여의서로"
        ));

        // 마포구
        DISTRICT_LOCATIONS.put("마포구", List.of(
                "홍대 관광특구", "홍대입구역(2호선)", "합정역", "망원한강공원",
                "연남동", "월드컵공원", "노들섬", "DMC(디지털미디어시티)", "신촌 스타광장"
        ));

        // 용산구
        DISTRICT_LOCATIONS.put("용산구", List.of(
                "이태원 관광특구", "용산역", "이태원역", "삼각지역",
                "이태원 앤틱가구거리", "국립중앙박물관·용산가족공원",
                "남산공원", "해방촌·경리단길", "용리단길", "이촌한강공원"
        ));

        // 성동구
        DISTRICT_LOCATIONS.put("성동구", List.of(
                "뚝섬역", "뚝섬한강공원", "성수카페거리",
                "왕십리역", "서울숲공원", "응봉산"
        ));

        // 광진구
        DISTRICT_LOCATIONS.put("광진구", List.of(
                "건대입구역", "어린이대공원", "아차산", "군자역"
        ));

        // 동작구
        DISTRICT_LOCATIONS.put("동작구", List.of(
                "사당역", "노량진", "보라매공원", "총신대입구(이수)역", "대림역"
        ));

        // 관악구
        DISTRICT_LOCATIONS.put("관악구", List.of(
                "서울대입구역", "신림역", "서울대공원"
        ));

        // 금천구
        DISTRICT_LOCATIONS.put("금천구", List.of(
                "가산디지털단지역"
        ));

        // 구로구
        DISTRICT_LOCATIONS.put("구로구", List.of(
                "신도림역", "구로디지털단지역", "구로역", "고척돔"
        ));

        // 양천구
        DISTRICT_LOCATIONS.put("양천구", List.of(
                "양화한강공원", "오목교역·목동운동장", "신정네거리역"
        ));

        // 강북구
        DISTRICT_LOCATIONS.put("강북구", List.of(
                "수유역", "미아사거리역"
        ));

        // 성북구
        DISTRICT_LOCATIONS.put("성북구", List.of(
                "성신여대입구역", "북서울꿈의숲"
        ));

        // 도봉구
        DISTRICT_LOCATIONS.put("도봉구", List.of(
                "창동 신경제 중심지", "쌍문역"
        ));

        // 노원구
        DISTRICT_LOCATIONS.put("노원구", List.of(
                "청계산"
        ));

        // 은평구
        DISTRICT_LOCATIONS.put("은평구", List.of(
                "연신내역", "홍제폭포"
        ));

        // 서대문구
        DISTRICT_LOCATIONS.put("서대문구", List.of(
                "신촌·이대역", "서대문독립공원"
        ));

        // 서초구
        DISTRICT_LOCATIONS.put("서초구", List.of(
                "양재역", "교대역", "서리풀공원·몽마르뜨공원",
                "반포한강공원", "잠원한강공원", "고속터미널역"
        ));

        // 중랑구
        DISTRICT_LOCATIONS.put("중랑구", List.of(
                "망우역", "상봉역"
        ));
    }

    /**
     * 모든 자치구 목록 반환
     */
    public List<String> getAllDistricts() {
        return new ArrayList<>(DISTRICT_LOCATIONS.keySet());
    }

    /**
     * 특정 자치구의 주요 핫스팟 반환
     */
    public List<String> getLocationsByDistrict(String district) {
        return DISTRICT_LOCATIONS.getOrDefault(district, new ArrayList<>());
    }

    /**
     * 키워드로 장소 검색 (자치구, 카테고리, 장소명)
     */
    public List<String> searchLocations(String keyword) {
        List<String> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        // 1. 자치구명 검색
        for (String district : DISTRICT_LOCATIONS.keySet()) {
            if (district.toLowerCase().contains(lowerKeyword)) {
                results.add("[자치구] " + district);
            }
        }

        // 2. 카테고리 검색
        for (String category : LocationCategory.getCategoryNames()) {
            if (category.toLowerCase().contains(lowerKeyword)) {
                results.add("[카테고리] " + category);
            }
        }

        // 3. 장소명 검색
        List<String> locationResults = LocationCategory.searchLocations(keyword);
        for (String location : locationResults) {
            results.add("[장소] " + location);
        }

        return results;
    }

    /**
     * 모든 카테고리와 장소 반환
     */
    public Map<String, List<String>> getAllCategories() {
        return LocationCategory.getAllCategories();
    }

    /**
     * 특정 카테고리의 장소 반환
     */
    public List<String> getLocationsByCategory(String category) {
        return LocationCategory.getLocationsByCategory(category);
    }
}
