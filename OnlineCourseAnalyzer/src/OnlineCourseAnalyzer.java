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

public class OnlineCourseAnalyzer {

    OnlineCourse[] onlineCourses;

    public OnlineCourseAnalyzer(String datasetPath) {

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
                (_1, _2) -> {
                }
            );
    }

    public Map<String, List<List<String>>> getCourseListOfInstructor() {
        Map<String, List<List<String>>> courseListOfInstructor = new LinkedHashMap<>();
        Set<String> instructorSet = new HashSet<>();
        for (OnlineCourse onlineCourse :
            onlineCourses) {
            Collections.addAll(instructorSet, onlineCourse.instructors);
        }
        for (String instructor :
            instructorSet) {
            List<List<String>> list = new ArrayList<>();
            List<String> list0 = new ArrayList<>();
            List<String> list1 = new ArrayList<>();
            for (OnlineCourse onlineCourse :
                onlineCourses) {
                if (onlineCourse.instructors.length == 1 && onlineCourse.instructors[0]
                    .equals(instructor)) {
                    list0.add(onlineCourse.courseTitle);
                } else {
                    for (String instr :
                        onlineCourse.instructors) {
                        if (instr.equals(instructor)) {
                            list1.add(onlineCourse.courseTitle);
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
        List<OnlineCourse> onlineCourseList = new ArrayList<>();
        if (by.equals("hours")) {
            onlineCourseList = Arrays.stream(onlineCourses)
                .sorted(Comparator.comparing(OnlineCourse::getTotalCourseHours).reversed()
                    .thenComparing(OnlineCourse::getCourseTitle))
                .limit(topK).toList();
        } else if (by.equals("participants")) {
            onlineCourseList = Arrays.stream(onlineCourses)
                .sorted(Comparator.comparing(OnlineCourse::getParticipants).reversed())
                .limit(topK).toList();
        } else {
            throw new NoSuchMethodException();
        }
        for (OnlineCourse onlineCourse :
            onlineCourseList) {
            if (!course.contains(onlineCourse.courseTitle)) {
                course.add(onlineCourse.courseTitle);
            }
        }
        return course;
    }

    public List<String> searchCourses(String courseSubject, double
        percentAudited, double totalCourseHours) {
        return null;
    }

    public List<String> recommendCourses(int age, int gender, int
        isBachelorOrHigher) {
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        OnlineCourseAnalyzer onlineCourseAnalyzer = new OnlineCourseAnalyzer("./src/local.csv");
//        System.out.println(onlineCourseAnalyzer.getPtcpCountByInst());
//        System.out.println(onlineCourseAnalyzer.getPtcpCountByInstAndSubject());
//        System.out.println(onlineCourseAnalyzer.getCourseListOfInstructor());
        System.out.println(onlineCourseAnalyzer.getCourses(20, "participants"));
        //System.out.println(onlineCourseAnalyzer.onlineCourses[0]);
    }
}
