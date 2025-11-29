package util;

import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    // 문자 입력 메서드 - 문자 읽는 동작을 묶어서 처리하기
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // 숫자 입력 메서드 - 숫자 읽는 동작을 묶어서 처리하기
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }

    // Enter를 누르면 다음으로 넘어가도록 설정하는 메서드
    public static void pause() {
        System.out.print("\n계속하려면 Enter를 누르세요...");
        scanner.nextLine();
    }

    // 입력 받기 종료 메서드
    public static void close() {
        scanner.close();
    }
}
