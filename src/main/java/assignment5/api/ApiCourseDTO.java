package assignment5.api;

import assignment5.model.courseModel.Course;

public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public ApiCourseDTO(Course course) {
        this.courseId = course.getID();
        this.catalogNumber = course.getCatalogNumber();
    }

    public ApiCourseDTO(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }
}
