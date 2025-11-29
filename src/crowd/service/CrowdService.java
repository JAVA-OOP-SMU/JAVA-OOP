package crowd.service;

import crowd.api.CrowdApiClient;
import crowd.domain.CrowdInfo;

/**
 * 유동 인구 서비스
 */
public class CrowdService {
    private final CrowdApiClient crowdApiClient;
    
    public CrowdService(CrowdApiClient crowdApiClient) {
        this.crowdApiClient = crowdApiClient;
    }
    
    public CrowdInfo getHotSpots(String location) throws Exception {
        return crowdApiClient.fetchCrowdInfo(location);
    }
    
    public boolean isApiAvailable() {
        return crowdApiClient.checkConnection();
    }
}
