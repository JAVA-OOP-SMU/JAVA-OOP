import ai.service.AiSummaryService;
import crowd.service.CrowdService;
import crowd.service.EventService;
import crowd.ui.EventCrowdMenu;
import location.service.LocationService;
import location.ui.LocationMenu;
import system.service.SystemCheckService;
import util.ConsoleUtil;
import weather.service.WeatherService;
import weather.ui.WeatherMenu;

/**
 * 메인 메뉴 컨트롤러
 */
public class MenuController {
    private final WeatherMenu weatherMenu;
    private final EventCrowdMenu eventCrowdMenu;
    private final LocationMenu locationMenu;
    private final SystemCheckService systemCheckService;

    public MenuController(WeatherService weatherService, EventService eventService, CrowdService crowdService, AiSummaryService aiService, SystemCheckService systemCheckService) {
        this.weatherMenu = new WeatherMenu(weatherService, aiService);
        this.eventCrowdMenu = new EventCrowdMenu(eventService, crowdService, aiService);
        this.locationMenu = new LocationMenu(new LocationService());
        this.systemCheckService = systemCheckService;
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
                    eventCrowdMenu.show();
                    break;
                case 3:
                    // 지역 조회 로직
                    locationMenu.show();
                    break;
                case 4:
                    // 서비스 사용 가능 체크
                    systemCheckService.checkAllSystems();
                    ConsoleUtil.pause();
                    break;
                case 5:
                    // 서비스 종료 로직
                    System.out.println("서비스가 종료됩니다.");
                    ConsoleUtil.close();
                    return;
                case 6:
                    // 서비스 이용 도움말 로직
                    System.out.println("============ Seoul Life 서비스 가이드 ============\n");

                    System.out.println("[1. 날씨]");
                    System.out.println("  1.1 특정 지역 날씨 조회");
                    System.out.println("      - 서울시 25개 자치구의 실시간 대기질 정보 조회");
                    System.out.println("      - PM10, PM2.5, 통합대기환경지수 제공");
                    System.out.println("  1.2 서울 전체 날씨 조회");
                    System.out.println("      - 서울시 전체 자치구의 대기질 정보 한눈에 보기");
                    System.out.println("  1.3 AI 옷차림 추천 (OpenAI 기반)");
                    System.out.println("      - 대기질 정보를 기반으로 마스크 착용 여부 및 옷차림 추천");
                    System.out.println("      - 사용 전 반드시 특정 지역 날씨를 먼저 조회해주세요\n");

                    System.out.println("[2. 행사 & 유동 인구]");
                    System.out.println("  2.1 오늘의 서울시 행사 조회");
                    System.out.println("      - 현재 진행 중인 서울시 문화/축제 행사 정보");
                    System.out.println("      - 지역명 입력 시 해당 지역 행사만 필터링 가능");
                    System.out.println("  2.2 관심 지역 유동 인구 조회");
                    System.out.println("      - 서울시 주요 120개 핫스팟의 실시간 혼잡도 정보");
                    System.out.println("      - 예: 강남역, 홍대입구역, 명동 관광특구 등");
                    System.out.println("  2.3 AI 활동 추천 (OpenAI 기반)");
                    System.out.println("      - 행사 정보와 유동인구 혼잡도를 분석하여 활동 추천");
                    System.out.println("      - 사용 전 행사 조회(2.1)와 유동인구 조회(2.2) 필요");
                    System.out.println("  2.4 AI 식사 메뉴 추천 (OpenAI 기반)");
                    System.out.println("      - 현재 시간대와 유동인구를 고려한 맞춤 메뉴 추천");
                    System.out.println("      - 사용 전 유동인구 조회(2.2) 필요\n");

                    System.out.println("[3. 지역 조회]");
                    System.out.println("  3.1 카테고리별 지역 조회");
                    System.out.println("      - 관광특구, 발달상권, 공원, 인구밀집지역 등 카테고리별 검색");
                    System.out.println("  3.2 자치구별 지역 조회");
                    System.out.println("      - 25개 자치구별로 주요 장소 목록 확인");
                    System.out.println("  3.3 장소명 검색");
                    System.out.println("      - 특정 장소명으로 직접 검색\n");

                    System.out.println("[4. 시스템 점검]");
                    System.out.println("  - 서울 열린데이터 광장 API 연결 상태 확인");
                    System.out.println("  - OpenAI API 연결 상태 확인");
                    System.out.println("  - 각 기능 사용 가능 여부 실시간 체크\n");

                    System.out.println("[5. 서비스 종료]");
                    System.out.println("  - 프로그램 종료\n");

                    System.out.println("================================================\n");
                    System.out.println("이용 주의 사항 Tip: AI 추천 기능 사용 시 반드시 해당 데이터를 먼저 조회해주세요!");
                    System.out.println(" AI 옷차림 추천 → 특정 지역 날씨 조회 필요");
                    System.out.println(" AI 활동 추천 → 행사 + 유동인구 조회 필요");
                    System.out.println(" AI 식사 메뉴 추천 → 유동인구 조회 필요\n");

                    ConsoleUtil.pause();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    ConsoleUtil.pause();
            }
        }
    }
}
