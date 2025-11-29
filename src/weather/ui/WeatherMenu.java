package weather.ui;


import ai.service.AiSummaryService;
import util.ConsoleUtil;
import weather.domain.Weather;
import weather.service.WeatherService;

import java.util.List;

/**
 * 날씨 메뉴 UI
 */
public class WeatherMenu {
    private final WeatherService weatherService;
    private final AiSummaryService aiService;
    private Weather lastWeather;

    public WeatherMenu(WeatherService weatherService, AiSummaryService aiService) {
        this.weatherService = weatherService;
        this.aiService = aiService;
    }

    public void show() {
        while (true) {
            System.out.println("날씨 메뉴");
            System.out.println("1. 특정 지역 날씨 조회");
            System.out.println("2. 서울 전체 날씨 조회");
            System.out.println("3. AI 옷차림 추천");
            System.out.println("4. 돌아가기\n");

            int choice = ConsoleUtil.readInt("선택 > ");
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        // 특정 지역 날씨 조회
                        showSpecificWeather();
                        break;

                    case 2:
                        // 서울 전체 날씨 조회
                        showAllWeather();
                        break;

                    case 3:
                        // 날씨 기반 AI 옷차림 추천
                        showOutfitRecommendation();
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

    private void showSpecificWeather() throws Exception {
        System.out.println("서울시 주요 구:");
        System.out.println("종로구, 중구, 강남구, 송파구, 영등포구, 마포구 등");
        System.out.println();

        String location = ConsoleUtil.readLine("지역명 입력 > ");

        System.out.println("\n 날씨 정보를 가져오는 중...\n");

        lastWeather = weatherService.getWeather(location);

        System.out.println(lastWeather);
        System.out.println("\n");
    }

    private void showAllWeather() throws Exception {
        System.out.println(" 서울 전체 날씨 정보를 가져오는 중...\n");

        List<Weather> weatherList = weatherService.getAllWeather();

        System.out.println("서울 전체 날씨");
        for (Weather weather : weatherList) {
            System.out.println(weather);
        }
        System.out.println();
    }

    private void showOutfitRecommendation() {
        if (lastWeather == null) {
            System.out.println("먼저 특정 지녁의 날씨를 조회해주세요!\n" +
                               "해당 지역을 기반으로 AI가 추천을 진행합니다.");
            return;
        }

        System.out.println("AI 옷차림 추천");
        String recommendation = aiService.recommendOutfit(lastWeather);
        System.out.println(recommendation);
    }
}
