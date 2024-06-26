package common.ui;

import java.util.Scanner;

public class Console {
    private static Console console = null;
    private Scanner systemIn;

    private Console() {}

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void println() {
        System.out.println();
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public void printError(Object obj) {
        System.err.println("# Error: " + obj);
    }

    public String input() {
        return this.systemIn.nextLine();
    }

    public void setScanner(Scanner scanner) {
        this.systemIn = scanner;
    }

    public Scanner getScanner() {
        return this.systemIn;
    }

    public static Console getInstance() {
        if (console == null) console = new Console();
        return console;
    }
}
