package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 환경 변수(.env) 로더
 * 프로그램 전체에서 API 키를 쉽게 가져다 쓸 수 있도록 돕는 유틸리티 클래스
 */
public class ApiConfig {

    // 로드된 환경변수를 저장할 저장소 (static으로 전역 공유)
    private static final Map<String, String> envMap = new HashMap<>();

    // 클래스가 처음 사용될 때 static 블록이 실행되어 .env 파일을 읽습니다.
    static {
        loadEnv();
    }

    private static void loadEnv() {
        // 프로젝트 루트 경로의 .env 파일을 가리킵니다.
        String filePath = ".env";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 주석(#)이나 빈 줄 무시
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }

                // KEY=VALUE 분리
                String[] parts = line.split("=", 2);
                if (parts.length >= 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    // 따옴표가 있다면 제거 (예: "KEY"="VALUE" -> KEY=VALUE)
                    value = value.replace("\"", "").replace("'", "");
                    envMap.put(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ [ApiConfig] .env 파일을 읽을 수 없습니다. (경로 확인 필요)");
            // 파일이 없으면 그냥 진행 (나중에 get 할 때 null 체크)
        }
    }

    /**
     * 외부에서 키 값을 가져올 때 사용하는 메서드
     * 예: ApiConfig.get("SEOUL_API_KEY")
     */
    public static String get(String key) {
        String value = envMap.get(key);
        if (value == null) {
            System.err.println("⚠️ 경고: 환경변수 키를 찾을 수 없습니다 -> " + key);
            return ""; // null 대신 빈 문자열 반환 (NullPointerException 방지) 또는 null 반환
        }
        return value;
    }
}