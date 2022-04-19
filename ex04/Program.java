import java.util.Scanner;

public class Program {
    private static final int UNICE_CHAR_AMOUNT = 65535;
    private static final int VISIBLE_CHAR_AMOUNT = 10;

    private static final int MAX_COLUM_LENGTH = 10;
    private static int maxOccurrenceIndex;

    public static void main(String[] args) {
        int[] charOccurrence = new int[UNICE_CHAR_AMOUNT];
        Scanner console = new Scanner(System.in);
        String string = console.nextLine();
        int stringLength = string.length();
        for (int charIndex = 0; charIndex < stringLength; ++charIndex) {
            if (charOccurrence[string.toCharArray()[charIndex]] < 999) {
                ++(charOccurrence[string.toCharArray()[charIndex]]);
            }
        }
        int maxOccurrenceIndexesSize = getUniceCharAmount(charOccurrence);
        if (maxOccurrenceIndexesSize != 0) {
            if (maxOccurrenceIndexesSize > VISIBLE_CHAR_AMOUNT) {
                maxOccurrenceIndexesSize = VISIBLE_CHAR_AMOUNT;
            }
            int[] maxOccurrenceIndexes = new int[maxOccurrenceIndexesSize];
            for (int charIndex = 0; charIndex < UNICE_CHAR_AMOUNT; ++charIndex) {
                if (charOccurrence[charIndex] > charOccurrence[maxOccurrenceIndexes[maxOccurrenceIndexes.length - 1]]) {
                    addToMaxOccurrenceIndexes(charOccurrence, charIndex, maxOccurrenceIndexes);
                }
            }
            int totalOccurrence = findTotalOccurrence(charOccurrence, maxOccurrenceIndexes);
            int[] visibleOccurrence = new int[maxOccurrenceIndexesSize];
            if (totalOccurrence != 0) {
                for (int percentOccurrenceIndex = 0; percentOccurrenceIndex < maxOccurrenceIndexesSize; ++percentOccurrenceIndex) {
                    visibleOccurrence[percentOccurrenceIndex] = charOccurrence[maxOccurrenceIndexes[percentOccurrenceIndex]];
                }
            }
            printOccurrence(visibleOccurrence, maxOccurrenceIndexes);
        }

    }

    private static int getUniceCharAmount (int [] charOccurrence) {
        int result = 0;
        for (int counter = 0; counter < charOccurrence.length; ++counter) {
            if (charOccurrence[counter] != 0) {
                ++result;
            }
        }
        return result;
    }
    private static int findTotalOccurrence(int [] charOccurrence, int [] maxOccurrence) {
        int result = 0;
        for (int counter = 0; counter < maxOccurrence.length; ++counter) {
            result += charOccurrence[maxOccurrence[counter]];
        }
        return result;
    }
    private static void addToMaxOccurrenceIndexes (int [] charOccurrence, int charIndex, int [] maxOccurrence) {
        for (int maxOccurrenceCounter = 0; maxOccurrenceCounter < maxOccurrence.length; ++maxOccurrenceCounter) {
            if (charOccurrence[maxOccurrence[maxOccurrenceCounter]] < charOccurrence[charIndex]) {
                for (int tailCounter = maxOccurrence.length - 1; tailCounter > maxOccurrenceCounter; --tailCounter) {
                    maxOccurrence[tailCounter] = maxOccurrence[tailCounter - 1];
                }
                maxOccurrence[maxOccurrenceCounter] = charIndex;
                return;
            }
        }
    }

    private static void printNumber(int number, int numeralsAmount) {
        String numberAsString = new String("");
        numberAsString += number;
        for (int spaceCounter = numeralsAmount - numberAsString.length(); spaceCounter > 0; --spaceCounter) {
            System.out.print(" ");
        }
        System.out.print(numberAsString);
    }
    private static void printOccurrence(int [] visibleOccurrence, int [] maxOccurrenceIndexes) {
        int columLength[] = new int[visibleOccurrence.length];
        columLength[0] = 10;
        for (int columLengthCounter = 1; columLengthCounter < visibleOccurrence.length; ++columLengthCounter) {
            columLength[columLengthCounter] = 10 * visibleOccurrence[columLengthCounter] / visibleOccurrence[0];
        }
        for (int verticalCounter = MAX_COLUM_LENGTH + 1; verticalCounter > 0; --verticalCounter) {
            for (int horizontalCounter = 0; horizontalCounter < visibleOccurrence.length; ++horizontalCounter) {
                if (verticalCounter == columLength[horizontalCounter] + 1) {
                    printNumber(visibleOccurrence[horizontalCounter], 3);
                } else if (verticalCounter <= columLength[horizontalCounter]) {
                    System.out.print("  #");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.print("\n");
        }
        for (int maxOccurrenceCounter = 0; maxOccurrenceCounter < maxOccurrenceIndexes.length; ++maxOccurrenceCounter) {
            System.out.print("  " + (char)( maxOccurrenceIndexes[maxOccurrenceCounter]));
        }
    }
}
