package crowd.domain;

public class CrowdInfo {
    private String location; // 장소명
    private String areaCode; // 장소 코드
    private String congestLevel; // 혼잡도 (여유/보통/약간붐빔/붐빔)
    private String congestMsg; // 혼잡도 메시지
    private String minPeople; // 최소 인구
    private String maxPeople; // 최대 인구
    private String maleRate; // 남성 비율
    private String femaleRate; // 여성 비율
    private String updateTime; // 업데이트 시간

    // 유동인구 데이터 생성자
    public CrowdInfo(String location, String areaCode, String congestLevel,
                     String congestMsg, String minPeople, String maxPeople,
                     String maleRate, String femaleRate, String updateTime) {
        this.location = location;
        this.areaCode = areaCode;
        this.congestLevel = congestLevel;
        this.congestMsg = congestMsg;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.maleRate = maleRate;
        this.femaleRate = femaleRate;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return String.format("[%s] 혼잡도: %s | 예상 인구: %s~%s명\t" + "남성 비율 : %s & 여성 비율 : %s \n" +
                        "%s\n" +
                        "업데이트 시간: %s",
                location, congestLevel, minPeople, maxPeople, maleRate, femaleRate,
                congestMsg,
                updateTime);
    }

    public String getLocation() {
        return location;
    }
}
