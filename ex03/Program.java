public class Program {
    private static final String WEEK_TITLE = "Week ";
    private static final String EOF = "42";

    static long gradeStorage = 0;
    public static void main(String[] args) {
        String weekTitle;
        Scanner console = new Scanner(System.in);
        int weekCounter = 0;
        while (weekCounter < 18) {
            weekTitle = console.nextLine();
            if (weekTitle.equals(EOF)) {
                break;
            }
            if (!weekTitle.equals(WEEK_TITLE + (weekCounter + 1))) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int minGrade = 9;
            int currentGrade;
            for (int gradeCounter = 0; gradeCounter < 5; ++gradeCounter) {
                if (!console.hasNextInt()) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                currentGrade = console.nextInt();
                if (currentGrade > 9 || currentGrade < 1) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                if (currentGrade < minGrade) {
                    minGrade = currentGrade;
                }
            }
            addToGardeStore(minGrade, weekCounter);
            console.nextLine();
            ++weekCounter;
        }
        for (int currentWeek = 1; currentWeek <= weekCounter; ++currentWeek) {
            System.out.print(WEEK_TITLE + (currentWeek) + " ");
            paintArrow(getFromGardeStore());
        }
    }

    private static void paintArrow(int length) {
        while (length > 0) {
            System.out.print("=");
            --length;
        }
        System.out.println(">");
    }
    private static long power(long base, long power) {
        long result = 1;
        while (power > 0) {
            result *= base;
            --power;
        }
        return result;
    }

    private static void addToGardeStore(int grade, int position) {
        gradeStorage += (grade - 1) * power(9, position);
    }

    private static int getFromGardeStore() {
        int result = (int)(gradeStorage % 9 + 1);
        gradeStorage /= 9;
        return result;
    }
}
