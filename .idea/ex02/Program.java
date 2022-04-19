import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int sequence_amount = 0;
        Scanner console = new Scanner(System.in);
        int currSequence = 0;
        do {
            if (!console.hasNextInt()) {
                System.err.println("IllegalArgument");
            } else {
                currSequence = console.nextInt();
            }
            if (isPrimal(numeralsSum(currSequence))) {
                ++sequence_amount;
            }
        }
        while (currSequence != 42);
        System.out.println("Count of coffee - request - " + sequence_amount);
        System.exit(0);
    }
    static private boolean isPrimal(int num) {
        if (num < 2) {
            return false;
        }
        int remainder = 1;
        if (num > 3) {
            if (num % 2 != 0) {
                int currentDivider = 3;
                while (((long) num + 1 - (long) currentDivider * (long) currentDivider) * remainder > 0) {
                    remainder = num % currentDivider;
                    currentDivider += 2;
                }
            }
            else
            {
                remainder = 0;
            }
        }
        return (remainder != 0);
    }

    private static int numeralsSum(int number) {
        if (number == 0) {
            return (1);
        }
        int result = 0;
        while (number / 10 != 0) {
            result += number % 10;
            number /= 10;
        }
        result += number;
        if (result < 0) {
            result = -result;
        }
        return (result);
    }
}
