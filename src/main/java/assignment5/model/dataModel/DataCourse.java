package assignment5.model.dataModel;

import assignment5.api.ApiOfferingDataDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Record DataCourse generates a temporary course object representing all data found in a singular course listing.
 * Includes semesterCode, subject, catalogNumber, location, capacity, total, instructors, and componentCode.
 * Class is solely intended to store valid data prior to input into all Course database.
 */

public record DataCourse(String semesterCode, String subject, String catalogNumber, String location,
                         int enrolmentCapacity, int enrolmentTotal, List<String> instructors,
                         String componentCode) {

    public static DataCourse fromCVSList(List<String> dataList) {
        String semesterCode = dataList.removeFirst();
        String subject = dataList.removeFirst();
        String catalogNumber = dataList.removeFirst();
        String location = dataList.removeFirst();
        int enrolmentCapacity = Integer.parseInt(dataList.removeFirst());
        int enrolmentTotal = Integer.parseInt(dataList.removeFirst());
        String componentCode = dataList.removeLast();

        List<String> instructors = new ArrayList<>();
        boolean noInstructor = dataList.getFirst().equals("<null>") || dataList.getFirst().equals("(null)");
        if (!noInstructor) {
            for (String instructor : dataList) {
                if (instructor.startsWith("\"")) {
                    instructor = instructor.substring(1);
                }
                if (instructor.endsWith("\"")) {
                    instructor = instructor.substring(0, instructor.length() - 1);
                }
                instructors.add(instructor.trim());
            }
        }
        return new DataCourse(semesterCode, subject, catalogNumber, location, enrolmentCapacity,
                enrolmentTotal, instructors, componentCode);
    }

}
