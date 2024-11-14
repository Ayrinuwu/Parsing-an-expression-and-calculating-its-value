package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        MathCalc calc = new MathCalc(System.in);
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        try {
            System.out.println(calc.calc(input));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}