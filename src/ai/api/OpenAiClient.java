package ai.api;

import util.ApiConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * OpenAI API 클라이언트
 * ChatGPT API와 통신하여 응답을 받아오는 클래스
 */
public class OpenAiClient {
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;

    public OpenAiClient() {
        // .env 파일에서 OPENAI_API_KEY 읽어오기
        this.apiKey = ApiConfig.get("OPENAI_API_KEY");
    }

    /**
     * OpenAI ChatGPT API 호출
     * @param prompt 사용자 프롬프트 (질문/요청 내용)
     * @return AI의 응답 텍스트
     * @throws Exception API 호출 실패 시
     */
    public String getChatCompletion(String prompt) throws Exception {
        // API 키 체크
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("OPENAI_API_KEY가 설정되지 않았습니다. .env 파일을 확인하세요.");
        }

        // HTTP 연결 설정
        URL url = new URL(OPENAI_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);

        // 요청 본문 생성 (JSON 형식)
        // gpt-4o-mini 모델 사용 (비용 효율적)
        String jsonRequest = String.format(
            "{\"model\": \"gpt-4o-mini\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"temperature\": 0.7}",
            escapeJson(prompt)
        );

        // 요청 전송
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 응답 코드 확인
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("OpenAI API 호출 실패: HTTP " + responseCode);
        }

        // 응답 읽기
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        // JSON 파싱 (간단한 문자열 파싱)
        return parseResponse(response.toString());
    }

    /**
     * OpenAI API JSON 응답에서 content 추출
     */
    private String parseResponse(String jsonResponse) {
        // JSON 라이브러리 없이 간단하게 파싱
        // "content": "응답내용" 형태에서 응답내용만 추출
        int contentIndex = jsonResponse.indexOf("\"content\":");
        if (contentIndex == -1) {
            return "응답을 파싱할 수 없습니다.";
        }

        int startQuote = jsonResponse.indexOf("\"", contentIndex + 10);
        int endQuote = jsonResponse.indexOf("\"", startQuote + 1);

        if (startQuote == -1 || endQuote == -1) {
            return "응답을 파싱할 수 없습니다.";
        }

        String content = jsonResponse.substring(startQuote + 1, endQuote);

        // 이스케이프 문자 처리
        content = content.replace("\\n", "\n")
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\");

        return content;
    }

    /**
     * JSON 문자열 이스케이프 처리
     */
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    /**
     * API 연결 테스트
     */
    public boolean testConnection() {
        try {
            String response = getChatCompletion("OPNE AI API 연결 테스트를 진행하려고 합니다.");
            return response != null && !response.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
