import java.util.Scanner;

public class Program {
    static int steps_counter = 0;
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        if (!console.hasNextInt()) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        int num = console.nextInt();
        if (num < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        if (isPrimal(num) == 0) {
            System.out.println(false + " " + steps_counter);
        }
        else {
            System.out.println(true + " " + steps_counter);
        }
        System.exit(0);
    }

    static private int isPrimal(int num) {
        int remainder = 1;
        int currentDivider = 2;
        while (isFirstHigherThenSecond(((long) num + 1 - (long) currentDivider * (long) currentDivider) * remainder, 0)) {
            remainder = num % currentDivider;
            currentDivider += 1;
        }
        return (remainder);
    }

    private static int isPrimal2(int num) {
        int remainder = 1;
        if (isFirstHigherThenSecond((long)num, 3L)) {
            if (notEqual((long)(num % 2), 0L)) {
                for(int currentDivider = 3; isFirstHigherThenSecond(((long)num + 1L - (long)currentDivider * (long)currentDivider) * (long)remainder, 0L); currentDivider += 2) {
                    remainder = num % currentDivider;
                }
            } else {
                remainder = 0;
            }
        }
        return remainder;
    }

    static private int isPrimal3(int num) {
        int currentDivider = 2;
        while (num + 1 - currentDivider * currentDivider > 0) {
            ++steps_counter;
            if (num % currentDivider == 0) {
                return (0);
            }
            currentDivider += 1;
        }
        return (1);
    }


    static private boolean isFirstHigherThenSecond(long first, long second) {
        ++steps_counter;
        if (first > second) {
            return true;
        }
        return false;
    }
    static private boolean notEqual(long first, long second) {
        ++steps_counter;
        if (first != second) {
            return true;
        }
        return false;
    }
}