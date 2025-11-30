import ai.service.AiSummaryService;
import crowd.api.CrowdApiClient;
import crowd.api.EventApiClient;
import crowd.api.impl.SeoulCrowdApiClient;
import crowd.api.impl.SeoulEventApiClient;
import crowd.service.CrowdService;
import crowd.service.EventService;
import system.service.SystemCheckService;
import util.ApiConfig;
import weather.api.WeatherApiClient;
import weather.api.impl.SeoulWeatherApiClient;
import weather.service.WeatherService;

/**
 * Seoul Life - 메인 실행
 *
 * 서울시 공공데이터 + AI 추천 서비스
 * - 날씨(대기질) 조회
 * - 유동 인구 조회 (120개 핫스팟)
 * - 행사 정보 조회
 * - AI 기반 옷차림, 활동, 식사 메뉴 추천
 */
public class SeoulLifeApp {
    public static void main(String[] args) {
        try {
            // ApiConfig를 통해 키 가져오기
            String seoulApiKey = ApiConfig.get("SEOUL_API_KEY");
            String OpenAiApiKey = ApiConfig.get("OPENAI_API_KEY");

            // 키 검증
            if (seoulApiKey.isEmpty() || OpenAiApiKey.isEmpty()) {
                throw new RuntimeException("API 키가 설정되지 않아 프로그램을 종료합니다.");
            }

            // API 클라이언트 초기화 (인터페이스 기반 다형성)
            WeatherApiClient weatherApiClient = new SeoulWeatherApiClient(seoulApiKey);
            CrowdApiClient crowdApiClient = new SeoulCrowdApiClient(seoulApiKey);
            EventApiClient eventApiClient = new SeoulEventApiClient(seoulApiKey);

            // 서비스 초기화
            WeatherService weatherService = new WeatherService(weatherApiClient);
            CrowdService crowdService = new CrowdService(crowdApiClient);
            EventService eventService = new EventService(eventApiClient);
            AiSummaryService aiService = new AiSummaryService();
            // 시스템 체크 서비스
            SystemCheckService systemCheckService = new SystemCheckService(weatherService, crowdService, eventService, aiService);

            // 메뉴 컨트롤러 초기화 및 실행
            MenuController menuController = new MenuController(weatherService, eventService, crowdService, aiService, systemCheckService);
            menuController.start();

        } catch (Exception e) {
            System.err.println("프로그램 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}