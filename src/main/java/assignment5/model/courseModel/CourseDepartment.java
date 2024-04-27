package assignment5.model.courseModel;

import assignment5.model.dataModel.DataCourse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class CourseDepartment holds all information about a particular department including the subject and courses.
 * In charge of delegating construction, watching, and display of all courses (continued chain).
 * Class relies on Course and DataCourse (always correct).
 */

public class CourseDepartment implements Comparable<CourseDepartment> {
    private long ID = -1;
    private final String subject;
    private final List<Course> courses = new ArrayList<>();

    public CourseDepartment(DataCourse data) {
        this.subject = data.subject();
        addCourse(data);
    }

    public void addCourse (DataCourse data) {
        Course course = findCourse(data.catalogNumber());
        if (course != null) {
            course.addOffering(data);
        } else {
            courses.add(new Course(data));
        }
    }

    private Course findCourse (String catalogNumber) {
        for (Course course : courses) {
            boolean equalCatalogNumber = course.getCatalogNumber().equals(catalogNumber);
            if (equalCatalogNumber) {
                return course;
            }
        }
        return null;
    }

    public void setIDs(long ID) {
        this.ID = ID;

        sortLists();
        for (int i = 0; i < courses.size(); i++) {
            courses.get(i).setIDs(i);
        }
    }

    public void resetID(long ID) {
        this.ID = ID;
    }

    public void resetInternalIDs(DataCourse data) {
        Course course = findCourse(data.catalogNumber());
        assert (course != null);
        if (course.getID() == -1) {
            sortLists();
            for (int i = 0; i < courses.size(); i++) {
                courses.get(i).resetID(i);
            }
        }
        course.resetInternalIDs(data);
    }

    public void display() {
        sortLists();
        for (Course course : courses) {
            course.display(subject);
        }
    }

    public String getSubject() {
        return subject;
    }

    public long getID() {
        return ID;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void sortLists() {
        courses.sort(null);
    }

    @Override
    public int compareTo(CourseDepartment other) {
        return this.subject.compareTo(other.subject);
    }
}
