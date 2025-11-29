package crowd.ui;

import ai.service.AiSummaryService;
import crowd.domain.CrowdInfo;
import crowd.domain.EventInfo;
import crowd.service.CrowdService;
import crowd.service.EventService;
import util.ConsoleUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 행사 & 유동 인구 메뉴 UI
 */
public class EventCrowdMenu {
    private final EventService eventService;
    private final CrowdService crowdService;
    private final AiSummaryService aiService;
    private List<EventInfo> lastEvents;
    private CrowdInfo lastCrowdInfo;

    public EventCrowdMenu(EventService eventService, CrowdService crowdService, AiSummaryService aiService) {
        this.eventService = eventService;
        this.crowdService = crowdService;
        this.aiService = aiService;
    }

    public void show() {
        while (true) {
            System.out.println("행사 & 유동 인구 메뉴");
            System.out.println("1. 오늘의 서울시 행사 조회");
            System.out.println("2. 관심 지역 유동 인구 조회");
            System.out.println("3. AI 활동 추천");
            System.out.println("4. AI 식사 메뉴 추천");
            System.out.println("5. 돌아가기\n");

            int choice = ConsoleUtil.readInt("선택 > ");
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        // 오늘의 행사 조회
                        showTodayEvents();
                        break;
                    case 2:
                        // 관심 지역 유동 인구 조회
                        showCrowdInfo();
                        break;
                    case 3:
                        // AI 활동 추천
//                        showActivityRecommendation(); -> 추후 구현
                        break;
                    case 4:
                        // AI 식사 메뉴 추천
//                        showMealRecommendation(); -> 추후 구현
                        break;
                    case 5:
                        // 돌아가기
                        return;
                    default:
                        System.out.println("잘못된 선택을 입력하였습니다.");
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }

            ConsoleUtil.pause();
        }
    }

    private void showTodayEvents() throws Exception {
        String location = ConsoleUtil.readLine("지역명 입력 (전체: Enter) > ");

        System.out.println("\n 행사 정보를 가져오는 중...\n");

        lastEvents = eventService.getTodayEvents(location.isEmpty() ? null : location);

        System.out.println("오늘의 행사");
        if (lastEvents.isEmpty()) {
            System.out.println("진행 중인 행사가 없습니다.");
        } else {
            for (int i = 0; i < lastEvents.size(); i++) {
                System.out.println((i + 1) + ". " + lastEvents.get(i));
                System.out.println();
            }
        }
        System.out.println();
    }

    private void showCrowdInfo() throws Exception {
        System.out.println(" 서울시 주요 120개 장소 중 선택하세요");
        System.out.println("예) 강남역, 홍대입구역(2호선), 광화문·덕수궁, 명동 관광특구");
        System.out.println();

        // 예시 장소 출력
        System.out.println("=== 주요 장소 예시 (총 120개) ===");
        String[] samples = {"강남역", "홍대입구역(2호선)", "명동 관광특구", "광화문·덕수궁",
                "여의도한강공원", "DDP(동대문디자인플라자)", "가로수길", "이태원 관광특구"};
        for (int i = 0; i < samples.length; i++) {
            System.out.print(samples[i]);
            if (i < samples.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n 이외에도 <3. 지역 조회>에서 행사 및 유동 인구 조회 가능한 지역을 조회할 수 있습니다.");

        String location = ConsoleUtil.readLine("\n장소명 입력 > ");
        System.out.println("\n 입력한 장소의 실시간 유동 인구 정보를 가져오는 중...\n");

        // 입력 받은 지역 정보를 이용하여 유동인구 정보 가져오기
        lastCrowdInfo = crowdService.getHotSpots(location);

        System.out.println("=== 유동 인구 정보 ===");
        System.out.println(lastCrowdInfo);
        System.out.println("\n");
    }

    private void showActivityRecommendation() {
        if (lastEvents == null || lastCrowdInfo == null) {
            System.out.println("먼저 행사와 유동 인구를 조회해주세요!");
            return;
        }

        System.out.println("AI 활동 추천");
        String recommendation = aiService.recommendActivity(lastEvents, lastCrowdInfo);
        System.out.println(recommendation + "\n");
    }

    private void showMealRecommendation() {
        if (lastCrowdInfo == null) {
            System.out.println("유동 인구 조회 이후에 가능합니다. 유동 인구 지역을 기반으로 추천합니다.");
            return;
        }

        System.out.println("=== AI 식사 메뉴 추천 ===");
        String recommendation = aiService.recommendMeal(lastCrowdInfo, lastCrowdInfo.getLocation(), LocalDateTime.now()
        );
        System.out.println(recommendation + "\n");
    }
}
