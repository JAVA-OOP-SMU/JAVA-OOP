package system.service;

import ai.service.AiSummaryService;
import crowd.service.CrowdService;
import crowd.service.EventService;
import weather.service.WeatherService;

/**
 * 시스템 점검 서비스
 */
public class SystemCheckService {
    private final WeatherService weatherService;
    private final CrowdService crowdService;
    private final EventService eventService;
    private final AiSummaryService aiService;
    
    public SystemCheckService(WeatherService weatherService, 
                            CrowdService crowdService,
                            EventService eventService,
                              AiSummaryService aiService) {
        this.weatherService = weatherService;
        this.crowdService = crowdService;
        this.eventService = eventService;
        this.aiService = aiService;
    }
    
    /**
     * 전체 시스템 점검
     */
    public void checkAllSystems() {
        System.out.println("---------- 시스템 점검 결과 ----------");

        // 날씨 API
        System.out.print("서울시 대기질 API: ");
        if (weatherService.isApiAvailable()) {
            System.out.println("정상");
        } else {
            System.out.println("연결 실패");
        }
        
        // 유동 인구 API
        System.out.print("서울시 유동 인구 API: ");
        if (crowdService.isApiAvailable()) {
            System.out.println("정상");
        } else {
            System.out.println("연결 실패");
        }
        
        // 행사 API
        System.out.print("서울시 행사 정보 API: ");
        if (eventService.isApiAvailable()) {
            System.out.println("정상");
        } else {
            System.out.println("연결 실패");
        }
        
        // AI API
        System.out.print("OpenAI API: ");
        if (aiService.isApiAvailable()) {
            System.out.println("정상");
        } else {
            System.out.println("연결 실패");
        }
    }
}
