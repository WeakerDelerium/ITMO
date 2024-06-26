package managers;

import java.util.Scanner;

public class Console {
    private static final Scanner systemIn = new Scanner(System.in);

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void println() {
        System.out.println();
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void printError(Object obj) {
        System.err.println("# Error: " + obj);
    }

    public static String input() {
        return systemIn.nextLine();
    }
}
