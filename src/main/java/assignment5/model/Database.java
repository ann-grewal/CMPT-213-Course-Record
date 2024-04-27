package assignment5.model;

import assignment5.model.courseModel.Course;
import assignment5.model.courseModel.CourseDepartment;
import assignment5.model.courseModel.CourseOffering;
import assignment5.model.dataModel.DataCourse;
import assignment5.model.dataModel.DataExtractorCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Database acts as a mock database sorting raw course data into sub tables for easy access.
 * In charge of delegating construction, watching, and display of all departments (head of chain).
 * Class loosely coupled to DataExtractorCSV but heavily relies on CourseDepartment and DataCourse (always correct).
 */

public class Database {
    final List<CourseDepartment> departments = new ArrayList<>();

    public static Database fromCSVData(String fileName) {
        Database database = new Database();
        DataExtractorCSV.CSVToList(fileName).stream()
                .map(DataCourse::fromCVSList)
                .forEach(database::addDepartment);
        database.setIDs();
        return database;
    }

    public void addData(DataCourse data) {
        addDepartment(data);
        resetInternalIDs(data);
    }

    private void addDepartment (DataCourse data) {
        CourseDepartment department = findDepartment(data.subject());
        if (department != null) {
            department.addCourse(data);
        } else {
            departments.add(new CourseDepartment(data));
        }
    }

    private CourseDepartment findDepartment (String subject) {
        for (CourseDepartment department : departments) {
            boolean equalSubjects = department.getSubject().equals(subject);
            if (equalSubjects) {
                return department;
            }
        }
        return null;
    }

    private void setIDs() {
        sortLists();
        for (int i = 0; i < departments.size(); i++) {
            departments.get(i).setIDs(i);
        }
    }

    public void resetInternalIDs(DataCourse data) {
        CourseDepartment department = findDepartment(data.subject());
        assert (department != null);

        if (department.getID() == -1) {
            sortLists();
            for (int i = 0; i < departments.size(); i++) {
                departments.get(i).resetID(i);
            }
        }
        department.resetInternalIDs(data);
    }

    public void display() {
        sortLists();
        System.out.println("Now Printing Course Data");
        for (CourseDepartment department : departments) {
            department.display();
        }
    }

    public List<CourseDepartment> getDepartments() {
        return departments;
    }

    public void sortLists() {
        departments.sort(null);
    }

    public String findEarliestSemester() {
        if (departments.isEmpty()) {
            return null;
        }
        return getDepartments()
                .stream()
                .map(CourseDepartment::getCourses)
                .flatMap(List::stream)
                .map(Course::getOfferings)
                .flatMap(List::stream)
                .min(CourseOffering::compareTo)
                .get()
                .getSemesterCode();
    }

    public String findLatestSemester() {
        if (departments.isEmpty()) {
            return null;
        }
        return getDepartments()
                .stream()
                .map(CourseDepartment::getCourses)
                .flatMap(List::stream)
                .map(Course::getOfferings)
                .flatMap(List::stream)
                .max(CourseOffering::compareTo)
                .get()
                .getSemesterCode();
    }

    public boolean isEmpty() {
        return departments.isEmpty();
    }
}
