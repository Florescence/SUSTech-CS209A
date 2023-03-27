import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OnlineCourse {

    String institution;
    String courseNumber;
    String launchDate;
    String courseTitle;
    String[] instructors;
    String courseSubject;
    int year;
    int honorCodeCertificate;
    int participants;
    int audited;
    int certified;
    double auditedPercentage;
    double certifiedPercentage;
    double certifiedOfMoreThan50PercentCourseContentAccessedPercentage;
    double playedVideoPercentage;
    double postedInForumPercentage;
    double gradeHigherThanZeroPercentage;
    double totalCourseHours;
    double medianHoursForCertification;
    double medianAge;
    double malePercentage;
    double femalePercentage;
    double bachelorDegreeOrHigherPercentage;
    double similarityValue = 2147483647;

    public double getSimilarityValue() {
        return similarityValue;
    }

    public String getInstitution() {
        return institution;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String[] getInstructors() {
        return instructors;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public int getYear() {
        return year;
    }

    public int getHonorCodeCertificate() {
        return honorCodeCertificate;
    }

    public int getParticipants() {
        return participants;
    }

    public int getAudited() {
        return audited;
    }

    public int getCertified() {
        return certified;
    }

    public double getAuditedPercentage() {
        return auditedPercentage;
    }

    public double getCertifiedPercentage() {
        return certifiedPercentage;
    }

    public double getCertifiedOfMoreThan50PercentCourseContentAccessedPercentage() {
        return certifiedOfMoreThan50PercentCourseContentAccessedPercentage;
    }

    public double getPlayedVideoPercentage() {
        return playedVideoPercentage;
    }

    public double getPostedInForumPercentage() {
        return postedInForumPercentage;
    }

    public double getGradeHigherThanZeroPercentage() {
        return gradeHigherThanZeroPercentage;
    }

    public double getTotalCourseHours() {
        return totalCourseHours;
    }

    public double getMedianHoursForCertification() {
        return medianHoursForCertification;
    }

    public double getMedianAge() {
        return medianAge;
    }

    public double getMalePercentage() {
        return malePercentage;
    }

    public double getFemalePercentage() {
        return femalePercentage;
    }

    public double getBachelorDegreeOrHigherPercentage() {
        return bachelorDegreeOrHigherPercentage;
    }

    public String getInstAndSubject() {
        return institution + "-" + courseSubject;
    }

    public OnlineCourse() {
    }
}

public class OnlineCoursesAnalyzer {

    OnlineCourse[] onlineCourses;

    public OnlineCoursesAnalyzer(String datasetPath) {

        ArrayList<String> lines = readCSV(datasetPath);

        List<List<String>> resList = splitCSV(lines);

        this.onlineCourses = initOnlineCourses(resList);
    }

    public static ArrayList<String> readCSV(String datasetPath) {
        File csv = new File(datasetPath);
        //csv.setReadable(true);
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(csv),
                StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line;
        int counter = 0;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while (true) {
                assert bufferedReader != null;
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                }
                // System.out.println(line);
                if (counter != 0) {
                    lines.add(line);
                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
        //System.out.println(lines);
    }

    public static List<List<String>> splitCSV(ArrayList<String> lines) {
        StringBuilder tempStr = new StringBuilder();
        List<List<String>> resList = new ArrayList<>();
        char specialChar = '\"';
        char splitChar = ',';

        for (String line : lines) {
            List<String> oneLine = new ArrayList<>();
            int len = line.toCharArray().length;
            // add the last element
            if (line.indexOf(specialChar) < 0) { // without \"
                for (int i = 0; i < len; i++) {
                    if (line.charAt(i) == splitChar) {
                        oneLine.add(tempStr.toString());
                        tempStr = new StringBuilder();
                    } else {
                        tempStr.append(line.charAt(i));
                    }
                }
            } else { // with \"
                oneLine = new ArrayList<>();
                for (int i = 0; i < len; i++) {
                    if (line.charAt(i) == specialChar) { // detect \"
                        for (int j = i + 1; ; j++) {
                            if (j < len) {
                                if (line.charAt(j) != specialChar) {
                                    tempStr.append(line.charAt(j));
                                } else {
                                    oneLine.add(tempStr.toString());
                                    tempStr = new StringBuilder();
                                    i = j + 1;
                                    break;
                                }
                            }
                        }
                    } else if (line.charAt(i) == splitChar) {
                        oneLine.add(tempStr.toString());
                        tempStr = new StringBuilder();
                    } else {
                        tempStr.append(line.charAt(i));
                    }
                }
            }
            if (!tempStr.toString().equals("")) { // add the last element
                oneLine.add(tempStr.toString());
                tempStr = new StringBuilder();
            }
            resList.add(oneLine);
        }
        //System.out.println(resList);
        return resList;
    }

    public static OnlineCourse[] initOnlineCourses(List<List<String>> resList) {
        OnlineCourse[] onlineCourses = new OnlineCourse[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            onlineCourses[i] = new OnlineCourse();
        }
        for (int i = 0; i < resList.size(); i++) {
            List<String> line = resList.get(i);
            onlineCourses[i].institution = line.get(0);
            onlineCourses[i].courseNumber = line.get(1);
            onlineCourses[i].launchDate = line.get(2);
            onlineCourses[i].courseTitle = line.get(3);
            onlineCourses[i].instructors = line.get(4).split(",");
            for (int j = 0; j < onlineCourses[i].instructors.length; j++) {
                for (int k = 1; k < onlineCourses[i].instructors[j].length(); k++) {
                    if (onlineCourses[i].instructors[j].charAt(k) == '(') {
                        onlineCourses[i].instructors[j] = onlineCourses[i].instructors[j]
                            .substring(0, k);
                        break;
                    }
                }
            }
            onlineCourses[i].courseSubject = line.get(5);
            onlineCourses[i].year = Integer.parseInt(line.get(6));
            onlineCourses[i].honorCodeCertificate = Integer.parseInt(line.get(7));
            onlineCourses[i].participants = Integer.parseInt(line.get(8));
            onlineCourses[i].audited = Integer.parseInt(line.get(9));
            onlineCourses[i].certified = Integer.parseInt(line.get(10));
            onlineCourses[i].auditedPercentage = Double.parseDouble(line.get(11));
            onlineCourses[i].certifiedPercentage = Double.parseDouble(line.get(12));
            onlineCourses[i].certifiedOfMoreThan50PercentCourseContentAccessedPercentage = Double
                .parseDouble(line.get(13));
            onlineCourses[i].playedVideoPercentage = Double.parseDouble(line.get(14));
            onlineCourses[i].postedInForumPercentage = Double.parseDouble(line.get(15));
            onlineCourses[i].gradeHigherThanZeroPercentage = Double.parseDouble(line.get(16));
            onlineCourses[i].totalCourseHours = Double.parseDouble(line.get(17));
            onlineCourses[i].medianHoursForCertification = Double.parseDouble(line.get(18));
            onlineCourses[i].medianAge = Double.parseDouble(line.get(19));
            onlineCourses[i].malePercentage = Double.parseDouble(line.get(20));
            onlineCourses[i].femalePercentage = Double.parseDouble(line.get(21));
            onlineCourses[i].bachelorDegreeOrHigherPercentage = Double.parseDouble(line.get(22));
            //System.out.println(onlineCourses[i]);
        }
        return onlineCourses;
    }

    public Map<String, Integer> getPtcpCountByInst() {
        return Arrays.stream(onlineCourses)
            .sorted(Comparator.comparing(OnlineCourse::getInstitution).reversed())
            .collect(Collectors.groupingBy(OnlineCourse::getInstitution,
                Collectors.summingInt(OnlineCourse::getParticipants)));
    }

    public Map<String, Integer> getPtcpCountByInstAndSubject() {
        return Arrays.stream(onlineCourses)
            .collect(
                Collectors.groupingBy(OnlineCourse::getInstAndSubject,
                    Collectors.summingInt(OnlineCourse::getParticipants)))
            .entrySet().stream()
            .sorted(Entry.comparingByKey())
            .sorted(Collections.reverseOrder(Comparator.comparingInt(Entry::getValue)))
            .collect(
                LinkedHashMap::new,
                (map, val) -> map.put(val.getKey(), val.getValue()),
                (a, b) -> {
                }
            );
    }

    public Map<String, List<List<String>>> getCourseListOfInstructor() {
        Map<String, List<List<String>>> courseListOfInstructor = new LinkedHashMap<>();
        Set<String> instructorSet = new HashSet<>();
        for (OnlineCourse onlineCourse : onlineCourses) {
            for (String instructor : onlineCourse.instructors) {
                instructorSet.add(instructor.trim());
            }
        }
        for (String instructor : instructorSet) {
            List<List<String>> list = new ArrayList<>();
            List<String> list0 = new ArrayList<>();
            List<String> list1 = new ArrayList<>();
            for (OnlineCourse onlineCourse : onlineCourses) {
                if (onlineCourse.instructors.length == 1) {
                    if (onlineCourse.instructors[0].trim().equals(instructor)) {
                        if (!list0.contains(onlineCourse.courseTitle)) {
                            list0.add(onlineCourse.courseTitle);
                        }
                    }
                } else {
                    for (String instr : onlineCourse.instructors) {
                        if (instr.trim().equals(instructor)) {
                            if (!list1.contains(onlineCourse.courseTitle)) {
                                list1.add(onlineCourse.courseTitle);
                            }
                        }
                    }
                }
            }
            list.add(list0.stream().sorted().toList());
            list.add(list1.stream().sorted().toList());
            courseListOfInstructor.put(instructor, list);
        }
        return courseListOfInstructor;
    }

    public List<String> getCourses(int topK, String by) throws NoSuchMethodException {
        List<String> course = new ArrayList<>();
        List<OnlineCourse> onlineCourseList;
        if (by.equals("hours")) {
            onlineCourseList = Arrays.stream(onlineCourses)
                .sorted(Comparator.comparing(OnlineCourse::getTotalCourseHours).reversed()
                    .thenComparing(OnlineCourse::getCourseTitle))
                .toList();
        } else if (by.equals("participants")) {
            onlineCourseList = Arrays.stream(onlineCourses)
                .sorted(Comparator.comparing(OnlineCourse::getParticipants).reversed()
                    .thenComparing(OnlineCourse::getCourseTitle))
                .toList();
        } else {
            throw new NoSuchMethodException();
        }
        for (OnlineCourse onlineCourse :
            onlineCourseList) {
            if (!course.contains(onlineCourse.courseTitle)) {
                course.add(onlineCourse.courseTitle);
            }
        }
        return course.stream().limit(topK).toList();
    }

    public List<String> searchCourses(String courseSubject, double
        percentAudited, double totalCourseHours) {
        List<OnlineCourse> onlineCourseList = Arrays.stream(onlineCourses)
            //.filter(s -> s.courseSubject.regionMatches(true, 0, courseSubject, 0, courseSubject.length()))
            .filter(s -> s.courseSubject.toLowerCase().contains(courseSubject.toLowerCase()))
            .filter(s -> s.auditedPercentage >= percentAudited)
            .filter(s -> s.totalCourseHours <= totalCourseHours)
            .toList();
        List<String> courses = new ArrayList<>();
        for (OnlineCourse onlineCourse :
            onlineCourseList) {
            if (!courses.contains(onlineCourse.courseTitle)) {
                courses.add(onlineCourse.courseTitle);
            }
        }
        return courses.stream().sorted().toList();
    }

    public List<String> recommendCourses(int age, int gender, int
        isBachelorOrHigher) {

        List<OnlineCourse> recommendCourseList = new ArrayList<>(); // restore course info
        List<String> recommendCourseTitleList = new ArrayList<>(); // the list returned
        Set<String> courseNumberSet = new HashSet<>();

        for (OnlineCourse onlineCourse : onlineCourses) {
            courseNumberSet.add(onlineCourse.courseNumber);
        }

        int i = 0;
        double[] averageMedianAge = new double[courseNumberSet.size()];
        double[] averageMalePercentage = new double[courseNumberSet.size()];
        double[] averageBDOHPercentage = new double[courseNumberSet.size()];

        for (String courseNumber : courseNumberSet) { // scan all the courseNumbers

            int counter = 0; // count # of courses with this courseNumber
            int p = 0; // the position in array of course of this courseNumber with the latestLaunchDate
            long latestLaunchDate = 0;

            for (int j = 0; j < onlineCourses.length;
                j++) { // get similarityValue for each course with current courseNumber
                if (onlineCourses[j].courseNumber.equals(courseNumber)) {
                    // capture single course with current courseNumber, analyze and return course with latestLaunchDate
                    averageMedianAge[i] += onlineCourses[j].medianAge;
                    averageMalePercentage[i] += onlineCourses[j].malePercentage;
                    averageBDOHPercentage[i] += onlineCourses[j].bachelorDegreeOrHigherPercentage;
                    counter++;

                    String[] currentLaunchDateString = onlineCourses[j].launchDate.split("/");
                    int currentLaunchDate = Integer.parseInt(currentLaunchDateString[2]) * 10000
                        + Integer.parseInt(currentLaunchDateString[0]) * 100
                        + Integer.parseInt(currentLaunchDateString[1]);
                    //System.out.println(currentLaunchDate);
                    if (currentLaunchDate > latestLaunchDate) {
                        latestLaunchDate = currentLaunchDate;
                        p = j;
                    }
                }
            }

            averageMedianAge[i] /= counter;
            averageMalePercentage[i] /= counter;
            averageBDOHPercentage[i] /= counter;
            /*  similarity formula:
             *   similarity value = (age - average Median Age)^2 + (gender * 100 - average Male)^2
             *   + (isBachelorOrHigher * 100- average Bachelor's Degree or Higher)^2  */
            onlineCourses[p].similarityValue = Math.pow(age - averageMedianAge[i], 2) + Math
                .pow(gender * 100 - averageMalePercentage[i], 2) + Math
                .pow(isBachelorOrHigher * 100 - averageBDOHPercentage[i], 2);
            //System.out.println(onlineCourses[p].similarityValue);
            boolean flag = true;

//            Exception in thread "main" java.util.ConcurrentModificationException
//            for (OnlineCourse c: recommendCourseList) {
//                if (c.courseTitle.equals(course.courseTitle)) {
//                    if (c.similarityValue < course.similarityValue){
//                        flag = false;
//                        break;
//                    }
//                    else{
//                        recommendCourseList.remove(c);
//                    }
//                }
//            }
            // same courseTitle not allowed.
            Iterator<OnlineCourse> iterator = recommendCourseList.iterator();
            while (iterator.hasNext()) {
                OnlineCourse next = iterator.next();
                if (next.courseTitle.equals(onlineCourses[p].courseTitle)) {
                    if (next.similarityValue < onlineCourses[p].similarityValue) {
                        flag = false;
                        break;
                    } else {
                        iterator.remove();
                    }
                }
            }
            if (flag) {
                recommendCourseList.add(onlineCourses[p]);
            }

            i++;
        }

        recommendCourseList = recommendCourseList.stream()
            .sorted(Comparator.comparing(OnlineCourse::getSimilarityValue)
                .thenComparing(OnlineCourse::getCourseTitle))
            .limit(10)
            .toList();

        for (OnlineCourse onlineCourse : recommendCourseList) {
            recommendCourseTitleList.add(onlineCourse.courseTitle);
        }
        return recommendCourseTitleList;
    }
}
