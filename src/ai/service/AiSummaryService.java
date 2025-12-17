package ai.service;

import ai.api.OpenAiClient;
import crowd.domain.CrowdInfo;
import crowd.domain.EventInfo;
import weather.domain.Weather;

import java.time.LocalDateTime;
import java.util.List;

// AI 요약 및 추천 서비스 - OpenAI API를 활용하여 날씨 정보를 기반으로 옷차림을 추천
public class AiSummaryService {
    private final OpenAiClient openAiClient;

    public AiSummaryService() {
        this.openAiClient = new OpenAiClient();
    }

    // 날씨 정보를 받아 AI로 옷차림 추천
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
                "당신은 패션과 일상 건강을 함께 고려하는 전문가입니다.\n" +
                "다음 서울시 %s 지역의 대기질 정보를 바탕으로 오늘 외출 시 적절한 옷차림과 주의사항을 추천해주세요.\n\n" +

                "[대기질 정보]\n" +
                "- 미세먼지(PM10): %s\n" +
                "- 초미세먼지(PM2.5): %s\n" +
                "- 통합대기환경지수 등급: %s\n" +
                "- 위치: %s\n\n" +

                "[작성 조건]\n" +
                "- 전체 분량은 3~5문장입니다.\n" +
                "- 각 문장은 줄바꿈으로 구분해주세요.\n" +
                "- 반드시 아래 내용을 모두 포함해야 합니다:\n" +
                "  1) 대기질 상태를 고려한 마스크 착용 권장 여부(이유 포함)\n" +
                "  2) 실외 활동 시 주의할 점 1가지\n" +
                "  3) 오늘의 추천 옷차림(상의/아우터/하의/신발 중 최소 2가지 이상 구체적으로)\n\n" +

                "[답변 톤]\n" +
                "- 친근하고 따뜻한 말투를 사용하되 과장된 표현은 피해주세요.\n" +
                "- 의학적 진단이나 단정적인 표현은 사용하지 마세요.",
                location,
                pm10, pm25,
                airQuality,
                location
        );
    }

    /**
     * 행사 정보와 유동인구를 기반으로 활동 추천 프롬프트 생성
     */
    private String buildActivityPrompt(List<EventInfo> events, CrowdInfo crowdInfo) {
        StringBuilder eventDetails = new StringBuilder();

        // 행사 정보 정리
        if (events != null && !events.isEmpty()) {
            eventDetails.append("오늘의 행사 목록:\n");
            int count = Math.min(events.size(), 5); // 최대 5개까지만
            for (int i = 0; i < count; i++) {
                EventInfo event = events.get(i);
                eventDetails.append(String.format("- %s (장소: %s)\n",
                        event.getTitle(), event.getPlace()));
            }
        } else {
            eventDetails.append("오늘 진행 중인 행사가 없습니다.\n");
        }

        // 유동인구 정보
        String crowdLevel = crowdInfo != null ? crowdInfo.getCongestionLevel() : "정보 없음";
        String location = crowdInfo != null ? crowdInfo.getLocation() : "서울";

        return String.format(
                "당신은 서울 지역을 잘 아는 여행 및 여가 활동 전문가입니다.\n" +
                "아래 정보를 바탕으로 오늘 %s 지역에서 즐기기 좋은 활동을 추천해주세요.\n\n" +

                "[행사 정보]\n" +
                "%s\n" +
                "[현재 유동인구 혼잡도]\n" +
                "- 혼잡도 수준: %s\n\n" +

                "[작성 조건]\n" +
                "- 전체 분량은 3~5문장입니다.\n" +
                "- 각 문장은 줄바꿈으로 구분해주세요.\n" +
                "- 반드시 아래 내용을 모두 포함해야 합니다:\n" +
                "  1) 오늘 추천하는 주요 활동 2가지 이상\n" +
                "  2) 혼잡도를 고려한 방문 또는 이동 팁 1가지\n" +
                "  3) 이 지역에서 특히 놓치면 아쉬운 포인트 1가지\n" +
                "- 행사 정보가 없을 경우, 대체할 만한 일반적인 활동 위주로 추천해주세요.\n\n" +

                "[답변 톤]\n" +
                "- 친근하고 활기찬 말투로 작성하되 광고처럼 답변하지 마세요.",
                location,
                eventDetails.toString(),
                crowdLevel
        );
    }

    /**
     * 유동인구와 시간대를 기반으로 식사 메뉴 추천 프롬프트 생성
     */
    private String buildMealPrompt(CrowdInfo crowdInfo, String location, LocalDateTime now) {
        String crowdLevel = crowdInfo != null ? crowdInfo.getCongestionLevel() : "보통";
        int hour = now.getHour();

        // 시간대 분석
        String mealTime;
        if (hour >= 7 && hour < 10) {
            mealTime = "아침";
        } else if (hour >= 11 && hour < 14) {
            mealTime = "점심";
        } else if (hour >= 17 && hour < 20) {
            mealTime = "저녁";
        } else if (hour >= 20 && hour < 23) {
            mealTime = "야식";
        } else {
            mealTime = "간식";
        }

        return String.format(
                "당신은 서울의 맛집을 잘 아는 음식 전문가입니다. 다음 정보를 바탕으로 %s 메뉴를 추천해주세요.\n\n" +
                        "위치: %s\n" +
                        "현재 시간: %d시\n" +
                        "유동인구 혼잡도: %s\n\n" +
                        "다음 내용을 포함하여 3-5문장으로 추천해주세요:\n" +
                        "1. 이 시간대와 지역에 어울리는 음식 메뉴 (구체적으로)\n" +
                        "2. 혼잡도를 고려한 식사 장소 선택 팁\n" +
                        "3. 이 지역의 특색 있는 음식이나 맛집 추천\n\n" +
                        "친근하고 맛있게 느껴지는 톤으로 작성해주세요. 한 문장 이후에는 줄바꿈을 이용해서 가독성을 높여주세요.",
                mealTime,
                location,
                hour,
                crowdLevel
        );
    }

    // 행사 정보와 유동 인구를 기반으로 AI 활동 추천
    public String recommendActivity(List<EventInfo> lastEvents, CrowdInfo lastCrowdInfo) {
        try {
            // 행사 정보와 유동인구 정보를 기반으로 프롬프트 생성
            String prompt = buildActivityPrompt(lastEvents, lastCrowdInfo);

            // OpenAI API 호출
            System.out.println("\n AI가 추천 활동을 생각하고 있습니다...\n");
            String recommendation = openAiClient.getChatCompletion(prompt);
            return recommendation;

        } catch (Exception e) {
            return "AI 추천 서비스를 사용할 수 없습니다: " + e.getMessage();
        }
    }

    // 유동인구와 위치, 시간대를 기반으로 AI 식사 메뉴 추천
    public String recommendMeal(CrowdInfo lastCrowdInfo, String location, LocalDateTime now) {
        try {
            // 유동인구와 시간대 정보를 기반으로 프롬프트 생성
            String prompt = buildMealPrompt(lastCrowdInfo, location, now);

            // OpenAI API 호출
            System.out.println("\n AI가 맛있는 식사 메뉴를 추천하고 있습니다...\n");
            String recommendation = openAiClient.getChatCompletion(prompt);
            return recommendation;

        } catch (Exception e) {
            return "AI 추천 서비스를 사용할 수 없습니다: " + e.getMessage();
        }
    }

    /**
     * AI 서비스 연결 테스트
     */
    public boolean isApiAvailable() {
        return openAiClient.testConnection();
    }
}
