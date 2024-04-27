package assignment5.api;

import assignment5.model.courseModel.CourseComponent;

public class ApiOfferingSectionDTO {
    public String type;
    public int enrollmentCap;
    public int enrollmentTotal;

    public ApiOfferingSectionDTO(CourseComponent component) {
        this.type = component.getComponentCode();
        this.enrollmentCap = component.getEnrolmentCapacity();
        this.enrollmentTotal = component.getEnrolmentTotal();
    }
}
