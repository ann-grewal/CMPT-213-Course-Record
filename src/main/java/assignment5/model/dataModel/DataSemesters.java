package assignment5.model.dataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class DatabaseSemesters generates the full range of semesters between a given start semester and end semester.
 * Helper class not directly coupled to any other class.
 */

public class DataSemesters {
    public static List<String> findSemesterRange(String earliestSemester, String latestSemester) {
        List<String> semesters = new ArrayList<>();

        String currentSemester = earliestSemester;
        while (!currentSemester.equals(latestSemester)){
            semesters.add(currentSemester);
            currentSemester = nextSemester(currentSemester);
        }
        semesters.add(latestSemester);
        return semesters;
    }

    private static String nextSemester(String semesterCode) {
        String year = semesterCode.substring(0, 3);
        String nextYear = String.valueOf(Integer.parseInt(year) + 1);
        if (semesterCode.endsWith("1")) {
            return year + "4";
        } else if (semesterCode.endsWith("4")) {
            return year + "7";
        } else {
            return nextYear + "1";
        }
    }
}
