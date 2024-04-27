package assignment5.api;

public class ApiGraphDataPointDTO {
    public long semesterCode;
    public long totalCoursesTaken;

    public ApiGraphDataPointDTO(long semesterCode, long totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }
}
