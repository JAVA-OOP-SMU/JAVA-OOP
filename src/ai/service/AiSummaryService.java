package ai.service;

import ai.api.OpenAiClient;
import crowd.domain.CrowdInfo;
import crowd.domain.EventInfo;
import weather.domain.Weather;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI 요약 및 추천 서비스
 * OpenAI API를 활용하여 날씨 정보를 기반으로 옷차림을 추천
 */
public class AiSummaryService {
    private final OpenAiClient openAiClient;

    public AiSummaryService() {
        this.openAiClient = new OpenAiClient();
    }

    /**
     * 날씨 정보를 기반으로 AI 옷차림 추천
     *
     * @param weather 날씨 정보
     * @return AI가 추천한 옷차림 설명
     */
    public String recommendOutfit(Weather weather) {
        try {
            // 날씨 정보를 기반으로 프롬프트 생성
            String prompt = buildOutfitPrompt(weather);

            // OpenAI API 호출
            System.out.println("\n AI가 옷차림을 생각하고 있습니다...\n");
            String recommendation = openAiClient.getChatCompletion(prompt);
            return recommendation;

        } catch (Exception e) {
            return "AI 추천 서비스를 사용할 수 없습니다: " + e.getMessage();
        }
    }

    /**
     * 날씨 정보를 기반으로 옷차림 추천 프롬프트 생성
     */
    private String buildOutfitPrompt(Weather weather) {
        // 대기질 등급 분석
        String pm10 = weather.getPm10();
        String pm25 = weather.getPm25();
        String airQuality = weather.getAirQualityGrade();
        String location = weather.getLocation();

        // 상세한 프롬프트 작성
        return String.format(
                "당신은 패션 전문가입니다. 다음 서울시 %s 지역의 대기질 정보를 바탕으로 오늘 외출할 때 적합한 옷차림을 추천해주세요.\n\n" +
                        "대기질 정보:%s, %s\n" +
                        "- 통합대기환경지수: %s\n" +
                        "- 위치: %s\n\n" +
                        "다음 내용을 포함하여 3-5문장으로 간결하게 추천해주세요:\n" +
                        "1. 대기질 상태에 따른 마스크 착용 여부\n" +
                        "2. 실외 활동 시 주의사항\n" +
                        "3. 오늘의 추천 옷차림 (구체적으로)\n\n" +
                        "친근하고 따뜻한 톤으로 작성해주세요.",
                "한 분장 이후에는 줄바꿈을 이용해서 가시성이 좋게 문장을 구성해주세요" +
                        location,
                pm10, pm25,
                location,
                airQuality
        );
    }

    /**
     * AI 서비스 연결 테스트
     */
    public boolean isApiAvailable() {
        return openAiClient.testConnection();
    }

    // 추후 개발 예정
    public String recommendActivity(List<EventInfo> lastEvents, CrowdInfo lastCrowdInfo) {
        return "1";
    }

    // 추후 개발 예정
    public String recommendMeal(CrowdInfo lastCrowdInfo, String location, LocalDateTime now) {
        return "2";
    }
}
