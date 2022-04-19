import java.util.Scanner;

public class Program {
    private static final int MAX_STUDENTS_AMOUNT = 10;
    private static final int MAX_CLASSES_AMOUNT = 70;
    private static final int PAINT_CELL_SIZE = 10;
    private static final String EOF = ".";
    private static final String HERE = "HERE";
    private static final int FIRST_SEPTEMBER_DAY_OF_WEEK = 1;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String[] students;
        {
            String[] tempStudents = new String[MAX_STUDENTS_AMOUNT];
            int studentCounter = 0;
            String currentString = console.nextLine();
            while (!currentString.equals(EOF)) {
                if (studentCounter >= MAX_STUDENTS_AMOUNT) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                tempStudents[studentCounter] = currentString;
                currentString = console.nextLine();
                ++studentCounter;
            }
            students = new String[studentCounter];
            for (int copyCounter = 0; copyCounter < students.length; ++copyCounter) {
                students[copyCounter] = tempStudents[copyCounter];
            }
        }
        short[] classesDates = new short[MAX_CLASSES_AMOUNT];
        short[] classesTime = new short[MAX_CLASSES_AMOUNT];
        short[] classesWeekDays = new short[MAX_CLASSES_AMOUNT];
        fillTimeTable(classesDates, classesTime, classesWeekDays, console);

        short[][] attendances = new short[students.length][MAX_CLASSES_AMOUNT];
        while (true) {
            String tempString = console.nextLine();
            if (tempString.equals(EOF)) {
                paintTabularForm(students, classesDates, classesTime, classesWeekDays, attendances);
            } else {
                char [] currentLine = tempString.toCharArray();
                String CurrentStudent = getStudentFromArray(currentLine);
                int currentStudentIndex = 0;
                for (;currentStudentIndex < students.length; ++currentStudentIndex) {
                    if (CurrentStudent.equals(students[currentStudentIndex]))
                        break;
                }
                short currentClassTime = getTimeFromArray(currentLine);
                short currentClassDate = getDateFromArray(currentLine);
                short currentAttendance = getAttendanceFromArray(currentLine);
                for (int counter = 0; counter < attendances[currentStudentIndex].length; ++counter) {
                    if (classesDates[counter] == currentClassDate && classesTime[counter] == currentClassTime) {
                        attendances[currentStudentIndex][counter] = currentAttendance;
                        break;
                    }
                }
            }
        }
    }

    private static void fillTimeTable(short[] classesDates, short[] classesTime, short[] classesWeekDays, Scanner console) {
        short[] tempClassesDays = new short[MAX_CLASSES_AMOUNT];
        short[] tempClassesTime = new short[MAX_CLASSES_AMOUNT];
        int classesCounter = 0;
        String classesTimeString = new String("");
        while (!classesTimeString.equals(EOF)) {
            if (classesCounter >= MAX_CLASSES_AMOUNT) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            if (console.hasNextInt()) {
                tempClassesTime[classesCounter] = console.nextShort();
            }
            classesTimeString = console.nextLine();
            if (classesTimeString.equals(EOF)) {
                break;
            }
            tempClassesDays[classesCounter] = stringWeekDayToShort(new String(classesTimeString.toCharArray(), 1, classesTimeString.length() - 1));
            ++classesCounter;
            for (int sortCounter = classesCounter - 1; sortCounter > 0; --sortCounter) {
                if (tempClassesDays[sortCounter] < tempClassesDays[sortCounter - 1]
                        || (tempClassesDays[sortCounter] == tempClassesDays[sortCounter - 1]
                        && tempClassesTime[sortCounter] < tempClassesTime[sortCounter])) {
                    tempClassesDays[sortCounter] += tempClassesDays[sortCounter - 1];
                    tempClassesDays[sortCounter - 1] = (short) (tempClassesDays[sortCounter] - tempClassesDays[sortCounter - 1]);
                    tempClassesDays[sortCounter] = (short) (tempClassesDays[sortCounter] - tempClassesDays[sortCounter - 1]);
                    tempClassesTime[sortCounter] += tempClassesTime[sortCounter - 1];
                    tempClassesTime[sortCounter - 1] = (short) (tempClassesTime[sortCounter] - tempClassesTime[sortCounter - 1]);
                    tempClassesTime[sortCounter] = (short) (tempClassesTime[sortCounter] - tempClassesTime[sortCounter - 1]);
                }
            }
        }
        int currentClass = -1;
        for (int searchCounter = 0; searchCounter < classesCounter; ++searchCounter) {
            if (tempClassesDays[searchCounter] >= FIRST_SEPTEMBER_DAY_OF_WEEK) {
                currentClass = searchCounter;
            }
        }
        int weekCounter;
        if (currentClass < 0) {
            currentClass = 0;
            weekCounter = 1;
        }
        else {
            weekCounter = 0;
        }
        for (int timeTableCounter = 0; timeTableCounter < MAX_CLASSES_AMOUNT;) {
            for (; currentClass < classesCounter && timeTableCounter < MAX_CLASSES_AMOUNT; ++currentClass) {
                classesDates[timeTableCounter] = (short) (tempClassesDays[currentClass] + 7 * weekCounter);
                classesTime[timeTableCounter] = tempClassesTime[currentClass];
                classesWeekDays[timeTableCounter] = tempClassesDays[currentClass];
                ++timeTableCounter;
            }
            currentClass = 0;
            ++weekCounter;
        }
    }

    private static String getStudentFromArray(char [] value) {
        int counter = 0;
        while (value[counter] != ' ') {
            ++counter;
        }
        return new String(value, 0, counter);
    }

    private static short getTimeFromArray(char [] value) {
        int start = 0;
        while (value[start] != ' ') {
            ++start;
        }
        ++start;
        return ((short)(value[start] - '0'));
    }

    private static short getDateFromArray(char [] value) {
        int start = 0;
        for (int spaceCounter = 0; spaceCounter < 2; ++spaceCounter ) {
            while (value[start] != ' ') {
                ++start;
            }
            ++start;
        }
        short result = 0;
        while (value[start] != ' ') {
            result = (short) (result * 10 + (value[start] - '0'));
            ++start;
        }
        return (result);
    }

    private static short getAttendanceFromArray(char [] value) {
        int start = 0;
        for (int spaceCounter = 0; spaceCounter < 3; ++spaceCounter ) {
            while (value[start] != ' ') {
                ++start;
            }
            ++start;
        }
        if (new String(value, start, value.length - start).equals(HERE)) {
            return (1);
        }
        return (-1);
    }

    private static short stringWeekDayToShort(String dayString) {
        switch (dayString) {
            case ("MO"):
                return 0;
            case ("TU"):
                return 1;
            case ("WE"):
                return 2;
            case ("TH"):
                return 3;
            case ("FR"):
                return 4;
            case ("SA"):
                return 5;
            case ("SU"):
                return 6;
            default:
                System.err.println("IllegalArgument");
                System.exit(-1);
        }
        return (-1);
    }

    private static String stringDayToString(short day) {
        switch (day) {
        case 0:
            return ("MO");
        case 1:
            return ("TU");
        case 2:
            return ("WE");
        case 3:
            return ("TH");
        case 4:
            return ("FR");
        case 5:
            return ("SA");
        case 6:
            return ("SU");
        }
    return ("ER");
    }

    private static void paintTabularForm(String [] students, short[] classesDays,
                                         short[] classesTime, short[] classesWeekDays,
                                         short[][] attendances) {
        for (int verticalCounter = 0; verticalCounter < students.length + 1; ++verticalCounter) {
            for (int horizontalCounter = 0; horizontalCounter < MAX_CLASSES_AMOUNT + 1; ++horizontalCounter) {
                if (verticalCounter == 0) {
                    if (horizontalCounter == 0) {
                        printSpaces(PAINT_CELL_SIZE);
                    } else if (classesDays[horizontalCounter - 1] <= 30) {
                        paintTableHeaderCell(classesDays[horizontalCounter - 1],
                                classesTime[horizontalCounter - 1],
                                classesWeekDays[horizontalCounter -1]);
                    }
                } else {
                    if (horizontalCounter == 0) {
                        paintStudentNameCell(students[verticalCounter - 1]);
                    } else if (classesDays[horizontalCounter - 1] <= 30) {
                        if (attendances[verticalCounter - 1][horizontalCounter - 1] == 0) {
                            printSpaces(PAINT_CELL_SIZE);
                        } else {
                            printNumber(attendances[verticalCounter - 1][horizontalCounter - 1], PAINT_CELL_SIZE);
                        }
                        System.out.print("|");
                    }
                }
            }
            System.out.print("\n");
        }
    }

    private static void printSpaces(int amount) {
        for (int counter = 0; counter < amount; ++counter) {
            System.out.print(" ");
        }
    }

    private static void paintStudentNameCell(String student) {
        for (int spaceCounter = 0; spaceCounter < PAINT_CELL_SIZE - student.length(); ++spaceCounter) {
            System.out.print(" ");
        }
        System.out.print(student);
    }

    private static void paintTableHeaderCell(short classesDays, short classesTime, short classesWeekDay) {
            System.out.print(classesTime + ":00 " + stringDayToString(classesWeekDay) + " ");
            printNumber(classesDays, 2);
            System.out.print("|");
    }

    private static void printNumber(int number, int numeralsAmount) {
        String numberAsString = new String("");
        numberAsString += number;
        for (int spaceCounter = numeralsAmount - numberAsString.length(); spaceCounter > 0; --spaceCounter) {
            System.out.print(" ");
        }
        System.out.print(numberAsString);
    }
}