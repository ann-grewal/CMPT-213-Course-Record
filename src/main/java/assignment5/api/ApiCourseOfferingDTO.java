package assignment5.api;

import assignment5.model.courseModel.CourseOffering;

public class ApiCourseOfferingDTO {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public int semesterCode;
    public int year;

    public ApiCourseOfferingDTO(CourseOffering offering) {
        this.courseOfferingId = offering.getID();
        this.location = offering.getLocation();
        this.instructors = offering.getInstructorsString();
        this.term = offering.getSemesterCodeTerm();
        this.semesterCode = Integer.parseInt(offering.getSemesterCode());
        this.year = offering.getSemesterCodeYear();
    }
}
