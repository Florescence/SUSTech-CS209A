import java.util.Scanner;

public class test {

    static OnlineCourseAnalyzer onlineCourseAnalyzer = new OnlineCourseAnalyzer("./src/local.csv");
    static Scanner in = new Scanner(System.in);

    public static void test0() {
        System.out.println("0. Reading dataset finished");
    }

    public static void test1() {
        System.out.println("1. Participants count by institution");
        System.out.println(onlineCourseAnalyzer.getPtcpCountByInst());
    }

    public static void test2() {
        System.out.println("2. Participants count by institution and course subject");
        System.out.println(onlineCourseAnalyzer.getPtcpCountByInstAndSubject());
    }

    public static void test3() {
        System.out.println("3. Course list by Instructor");
        System.out.println(onlineCourseAnalyzer.getCourseListOfInstructor());
    }

    public static void test4() throws NoSuchMethodException {
        System.out.println("4. Top courses");
        System.out.println("Please enter topK (an int):");
        int topK = in.nextInt();
        System.out.println("Please enter by [a String (hours / participants)]:");
        String by = in.next();
        System.out.println(onlineCourseAnalyzer.getCourses(topK, by));
    }

    public static void test5() {
        System.out.println("5. Search courses");
        System.out.println("Please enter courseSubject (a String):");
        String courseSubject = in.next();
        System.out.println("Please enter percentAudited (a double):");
        double percentAudited = in.nextDouble();
        System.out.println("Please enter totalCourseHours (a double):");
        double totalCourseHours = in.nextDouble();
        System.out.println(
            onlineCourseAnalyzer.searchCourses(courseSubject, percentAudited, totalCourseHours));
    }

    public static void test6() {
        System.out.println("6. Recommend courses");
        System.out.println("Please enter age (an int):");
        int age = in.nextInt();
        System.out.println("Please enter gender (0-female, 1-male):");
        int gender = in.nextInt();
        System.out.println(
            "Please enter isBachelorOrHigher (0-Not get bachelor degree, 1- Bachelor degree or higher):");
        int isBachelorOrHigher = in.nextInt();
        System.out.println(onlineCourseAnalyzer.recommendCourses(age, gender, isBachelorOrHigher));
    }

    public static void main(String[] args) throws NoSuchMethodException {
        test0();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }
}
