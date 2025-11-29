package crowd.api;

import crowd.domain.EventInfo;

import java.util.List;

// 행사 정보 API 클라이언트 인터페이스
public interface EventApiClient {

    // 오늘의 행사 정보 조회 / location-지역명 / 반환값 : 행사 정보 리스트
    List<EventInfo> fetchTodayEvents(String location) throws Exception;

    // API 연결 상태 확인
    boolean checkConnection();
}


