package assignment5.model.courseModel;

import assignment5.model.dataModel.DataCourse;

/**
 * Class Course holds all information about a particular component including the capacity, total, and component code.
 * In charge of only constructing and displaying itself (end of chain).
 * Class relies on DataCourse (always correct).
 */

public class CourseComponent implements Comparable<CourseComponent> {
    private final int enrolmentCapacity;
    private final int enrolmentTotal;
    private final String componentCode;

    public CourseComponent(DataCourse data) {
        this.enrolmentCapacity = data.enrolmentCapacity();
        this.enrolmentTotal = data.enrolmentTotal();
        this.componentCode = data.componentCode();
    }

    public void display() {
        System.out.println("\t\tType=" + componentCode + ", Enrollment=" + enrolmentTotal + "/" + enrolmentCapacity);
    }

    public String getComponentCode() {
        return componentCode;
    }

    public int getEnrolmentCapacity() {
        return enrolmentCapacity;
    }

    public int getEnrolmentTotal() {
        return enrolmentTotal;
    }

    @Override
    public int compareTo(CourseComponent other) {
        return this.componentCode.compareTo(other.componentCode);
    }
}
