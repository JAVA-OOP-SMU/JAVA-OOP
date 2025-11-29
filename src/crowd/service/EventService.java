package crowd.service;

import crowd.api.EventApiClient;
import crowd.domain.EventInfo;

import java.util.List;

/**
 * 행사 정보 서비스
 */
public class EventService {
    private final EventApiClient eventApiClient;

    public EventService(EventApiClient eventApiClient) {
        this.eventApiClient = eventApiClient;
    }

    // 오늘의 행사 정보 조회 / location-지역명 / 반환값 : 행사 정보 리스트
    public List<EventInfo> getTodayEvents(String location) throws Exception {
        return eventApiClient.fetchTodayEvents(location);
    }

    // API 연결 상태 확인
    public boolean isApiAvailable() {
        return eventApiClient.checkConnection();
    }
}
