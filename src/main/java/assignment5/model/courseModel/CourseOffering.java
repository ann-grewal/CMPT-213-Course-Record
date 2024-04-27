package assignment5.model.courseModel;

import assignment5.model.dataModel.DataCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Course holds all information about a particular offering including the location, instructors, and components.
 * In charge of delegating construction and display of all components (continued chain).
 * Class relies on CourseComponents and DataCourse (always correct).
 * Class also always logs how it was last changed in string format.
 */

public class CourseOffering implements Comparable<CourseOffering> {
    private final String location;
    private final String semesterCode;
    private final List<String> instructors = new ArrayList<>();
    private final List<CourseComponent> components = new ArrayList<>();
    private long ID = -1;
    private String lastUpdate = "";

    public CourseOffering(DataCourse data) {
        this.location = data.location();
        this.semesterCode = data.semesterCode();
        addData(data);
    }

    public void addData(DataCourse data) {
        String update = data.subject() + " " + data.catalogNumber() + " offered in " +
                getSemesterCodeTerm() + " " + getSemesterCodeYear() + " at " + location;
        update += addComponent(data);
        update += addInstructors(data);
        lastUpdate = update;
    }

    public String addInstructors(DataCourse data) {
        List<String> recentlyAdded = new ArrayList<>();
        for (String newInstructor : data.instructors()) {
            if (!findInstructor(newInstructor)) {
                instructors.add(newInstructor);
                recentlyAdded.add(newInstructor);
            }
        }
        if (recentlyAdded.isEmpty()) {
            return "";
        } else {
            return " and added instructors " + getInstructorsString(recentlyAdded);
        }
    }

    private boolean findInstructor(String newInstructor) {
        for (String oldInstructor : instructors) {
            if (oldInstructor.equals(newInstructor)) {
                return true;
            }
        }
        return false;
    }

    public String addComponent(DataCourse data) {
        components.add(new CourseComponent(data));
        return " added component " + data.componentCode() + " with enrollment " +
                data.enrolmentTotal() + " out of " + data.enrolmentCapacity();
    }

    public void setIDs(long ID) {
        this.ID = ID;
        sortLists();
    }

    public void resetID(long ID) {
        this.ID = ID;
    }

    public void resetInternalIDs() {
        sortLists();
    }

    public void display() {
        sortLists();
        System.out.println("\t" + semesterCode + " in " + location + " by " + getInstructorsString());
        for (CourseComponent component : components) {
            component.display();
        }
    }

    public String getInstructorsString() {
        return getInstructorsString(this.instructors);
    }

    private String getInstructorsString(List<String> instructorsList) {
        String instructorsString = "";
        for (String instructor : instructorsList) {
            if (instructor.equals(instructorsList.getFirst())) {
                instructorsString += instructor;
            } else {
                instructorsString += ", " + instructor;
            }
        }
        return instructorsString;
    }

    public String getLocation() {
        return location;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public int getSemesterCodeYear() {
        int hundreds = Integer.parseInt(semesterCode.substring(0, 1));
        int tens = Integer.parseInt(semesterCode.substring(1, 2));
        int ones = Integer.parseInt(semesterCode.substring(2, 3));
        return 1900 + 100 * hundreds + 10 * tens + ones;
    }

    public String getSemesterCodeTerm() {
        int term = Integer.parseInt(semesterCode.substring(3, 4));
        if (term == 1) {
            return "Spring";
        } else if (term == 4) {
            return "Summer";
        } else {
            return "Fall";
        }
    }

    public long getID() {
        return ID;
    }

    public List<CourseComponent> getComponents() {
        return components;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    private void sortLists() {
        components.sort(null);
        instructors.sort((String::compareTo));
    }

    @Override
    public int compareTo(CourseOffering other) {
        int result = this.semesterCode.compareTo(other.semesterCode);
        if (result == 0) {
            result = this.location.compareTo(other.location);
        }
        return result;
    }
}
