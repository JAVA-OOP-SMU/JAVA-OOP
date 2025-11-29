package location.ui;

import location.service.LocationService;
import util.ConsoleUtil;

import java.util.List;
import java.util.Map;

/**
 * 지역 조회 메뉴 UI
 * 카테고리, 자치구별, 검색으로 지역을 검색하고 조회 (혼잡도는 2번 메뉴에서 처리)
 */
public class LocationMenu {
    private final LocationService locationService;

    public LocationMenu(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * 지역 조회 메뉴 표시
     */
    public void show() {
        while (true) {
            System.out.println("= 서울 지역 조회 =");
            System.out.println("1. 카테고리별 (관광특구, 공원 등)");
            System.out.println("2. 자치구별");
            System.out.println("3. 장소명 검색");
            System.out.println("4. 돌아가기\n");

            int choice = ConsoleUtil.readInt("선택 > ");
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        // 카테고리별 지역 조회
                        browseByCategory();
                        break;

                    case 2:
                        // 자치구별 지역 조회
                        browseByDistrict();
                        break;

                    case 3:
                        // 검색어로 지역 조회
                        searchLocation();
                        break;

                    case 4:
                        return;
                    default:
                        System.out.println("잘못된 선택입니다.");
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }

            ConsoleUtil.pause();
        }
    }

    /**
     * 카테고리별 지역 조회
     */
    private void browseByCategory() {
        Map<String, List<String>> categories = locationService.getAllCategories();
        List<String> categoryNames = new java.util.ArrayList<>(categories.keySet());

        System.out.println("= 카테고리별 지역 =");
        for (int i = 0; i < categoryNames.size(); i++) {
            System.out.println((i + 1) + ". " + categoryNames.get(i) +
                " (" + categories.get(categoryNames.get(i)).size() + "개)");
        }
        System.out.println();

        int categoryChoice = ConsoleUtil.readInt("선택 > ");
        System.out.println();

        if (categoryChoice < 1 || categoryChoice > categoryNames.size()) {
            System.out.println("잘못된 선택입니다.");
            return;
        }

        String selectedCategory = categoryNames.get(categoryChoice - 1);
        List<String> locations = locationService.getLocationsByCategory(selectedCategory);

        System.out.println("= " + selectedCategory + " =");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ". " + locations.get(i));
        }
        System.out.println();
    }

    /**
     * 자치구별 지역 조회
     */
    private void browseByDistrict() {
        // 전체 자치구 리스트
        List<String> districts = locationService.getAllDistricts();

        System.out.println("자치구별 지역");
        for (int i = 0; i < districts.size(); i++) {
            System.out.println((i + 1) + ". " + districts.get(i));
        }
        System.out.println();

        // 번호 입력 받기
        int districtChoice = ConsoleUtil.readInt("선택 > ");
        System.out.println();

        if (districtChoice < 1 || districtChoice > districts.size()) {
            System.out.println("잘못된 선택입니다. 번호를 잘 입력하세요.");
            return;
        }

        String selectedDistrict = districts.get(districtChoice - 1);
        List<String> locations = locationService.getLocationsByDistrict(selectedDistrict);

        if (locations.isEmpty()) {
            System.out.println(selectedDistrict + "의 데이터가 없습니다.");
            return;
        }

        System.out.println("= " + selectedDistrict + " =");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ". " + locations.get(i));
        }
        System.out.println();
    }

    /**
     * 검색으로 지역 조회
     */
    private void searchLocation() {
        String keyword = ConsoleUtil.readLine("검색할 장소명 입력 > ");
        System.out.println();

        if (keyword.trim().isEmpty()) {
            System.out.println("검색어를 입력해주세요.");
            return;
        }

        // 존재하는 장소 전부를 가지는 리스트 생성
        List<String> allLocations = locationService.getAllCategories().values().stream()
            .flatMap(List::stream)
            .toList();

        // 입력한 장소가 다루는 데이터에 들어 있는 지 여부를 확인
        List<String> results = allLocations.stream()
            .filter(loc -> loc.toLowerCase().contains(keyword.toLowerCase()))
            .toList();

        if (results.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        System.out.println("= 검색한 결과: \"" + keyword + "\" =");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }
        System.out.println();
    }
}
