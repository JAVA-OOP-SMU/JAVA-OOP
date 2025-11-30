package crowd.domain;

/**
 * 서울시 문화행사 정보 도메인 클래스
 */
public class EventInfo {
    private String codeName; // 분류
    private String guName; // 자치구명
    private String title; // 공연/행사명
    private String date; // 날짜 (YYYY-MM-DD~YYYY-MM-DD)
    private String place; // 장소
    private String orgName; // 기관명
    private String useTarget; // 이용대상
    private String useFee; // 이용요금
    private String program; // 프로그램소개
    private String registDate; // 신청일
    private String ticket; // 시민/기관
    private String isFree; // 유료, 무료 여부
    private String homepageAddr; // 문화포털상세URL
    private String proTime; // 행사시간

    public EventInfo(String codeName, String guName, String title, String date,
                     String place, String orgName, String useTarget, String useFee,
                     String program, String registDate, String ticket, String isFree,
                     String homepageAddr, String proTime) {
        this.codeName = codeName;
        this.guName = guName;
        this.title = title;
        this.date = date;
        this.place = place;
        this.orgName = orgName;
        this.useTarget = useTarget;
        this.useFee = useFee;
        this.program = program;
        this.registDate = registDate;
        this.ticket = ticket;
        this.isFree = isFree;
        this.homepageAddr = homepageAddr;
        this.proTime = proTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");
        sb.append("분류: ").append(codeName).append("\n");
        sb.append("지역: ").append(guName).append("\n");
        sb.append("날짜: ").append(date).append("\n");
        sb.append("장소: ").append(place).append("\n");
        sb.append("기관: ").append(orgName).append("\n");

        if (useTarget != null && !useTarget.isEmpty()) {
            sb.append("이용대상: ").append(useTarget).append("\n");
        }

        if (useFee != null && !useFee.isEmpty()) {
            sb.append("이용요금: ").append(useFee).append("\n");
        } else {
            sb.append("요금: ").append(isFree).append("\n");
        }

        if (proTime != null && !proTime.isEmpty()) {
            sb.append("행사시간: ").append(proTime).append("\n");
        }

        if (program != null && !program.isEmpty() && !program.equals("정보 없음")) {
            sb.append("프로그램: ").append(program).append("\n");
        }

        if (registDate != null && !registDate.isEmpty() && !registDate.equals("정보 없음")) {
            sb.append("신청일: ").append(registDate).append("\n");
        }

        if (ticket != null && !ticket.isEmpty() && !ticket.equals("정보 없음")) {
            sb.append("티켓구분: ").append(ticket).append("\n");
        }

        if (homepageAddr != null && !homepageAddr.isEmpty() && !homepageAddr.equals("정보 없음")) {
            sb.append("상세정보: ").append(homepageAddr).append("\n");
        }
        return sb.toString();
    }

    // getter
    public String getTitle() {
        return title;
    }

    public String getPlace() {
        return place;
    }
}
