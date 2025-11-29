package crowd.api;

import crowd.domain.CrowdInfo;

// 유동 인구 API 클라이언트 인터페이스
public interface CrowdApiClient {
    
     // 특정 지역의 유동 인구 정보 조회 / location - 장소명 / return - 해당 장소의 실시간 유통 인구 정보
    CrowdInfo fetchCrowdInfo(String location) throws Exception;
    
     // API 사용 가능 상태 확인
    boolean checkConnection();
}
