package Util;

public class ConsoleUtil {

    public static void printTitle(String title) {
        System.out.println("\n==============================");
        System.out.println(title);
        System.out.println("==============================");
    }

    public static void printLine() {
        System.out.println("------------------------------");
    }

    public static void waitForEnter() {
        System.out.println("\nDevam etmek için Enter'a bas...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}
