import location.service.LocationService;
import location.ui.LocationMenu;
import util.ConsoleUtil;
import weather.service.WeatherService;
import weather.ui.WeatherMenu;

/**
 * 메인 메뉴 컨트롤러
 */
public class MenuController {
    private final WeatherMenu weatherMenu;
    private final LocationMenu locationMenu;

    public MenuController(WeatherService weatherService) {
        this.weatherMenu = new WeatherMenu(weatherService);
        this.locationMenu = new LocationMenu(new LocationService());
    }

    public void start() {
        System.out.println("\n");
        System.out.println("============== Seoul Life - 나의 즐거운 서울 생활 ==============");
        System.out.println("      서울시 실시간 공공데이터 API를 사용한 + AI 추천 서비스\n");
        ConsoleUtil.pause();

        while (true) {
            System.out.println("========================================");
            System.out.println("1. 날씨");
            System.out.println("2. 행사 & 유동 인구");
            System.out.println("3. 지역 조회");
            System.out.println("4. 시스템 점검");
            System.out.println("5. 서비스 종료");
            System.out.println("6. Help (서비스 기능 설명)\n");

            int choice = ConsoleUtil.readInt("선택 > ");
            System.out.println();

            switch (choice) {
                case 1:
                    // 날씨 관련 로직
                    weatherMenu.show();
                    break;
                case 2:
                    // 행사와 유동 인구 관련 로직

                    break;
                case 3:
                    // 지역 조회 로직
                    locationMenu.show();
                    break;
                case 4:
                    // 서비스 사용 가능 체크

                    ConsoleUtil.pause();
                    break;
                case 5:
                    // 서비스 종료 로직
                    System.out.println("서비스가 종료됩니다.");
                    ConsoleUtil.close();
                    return;
                case 6:
                    // 서비스 이용 도움말 로직
                    System.out.println("[1. 날씨]");
                    System.out.println("  1.1 특정 지역 날씨 조회 - 자치구명 입력");
                    System.out.println("  1.2 서울 전체 날씨 조회");
                    System.out.println("  1.3 AI 옷차림 추천 (날씨 기반)\n");

                    System.out.println("[2. 행사 & 유동 인구]");
                    System.out.println("  2.1 오늘의 서울시 행사 조회");
                    System.out.println("  2.2 관심 지역 유동 인구 조회 (120개 핫스팟)");
                    System.out.println("  2.3 AI 활동 추천 (행사 + 유동 인구 기반)");
                    System.out.println("  2.4 AI 식사 메뉴 추천 (유동 인구 기반)\n");

                    System.out.println("[3. 지역 조회]");
                    System.out.println("  3.1 카테고리별 지역 조회 (관광특구, 공원 등)");
                    System.out.println("  3.2 자치구별 지역 조회");
                    System.out.println("  3.3 장소명 검색");

                    System.out.println("[4. 시스템 점검]");
                    System.out.println("  - API 연결 상태 확인\n");

                    System.out.println("[5. 서비스 종료]");
                    System.out.println("  - 프로그램 종료\n");
                    ConsoleUtil.pause();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    ConsoleUtil.pause();
            }
        }
    }
}
