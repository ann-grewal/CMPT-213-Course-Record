package assignment5.controller;

import assignment5.api.*;
import assignment5.model.Database;
import assignment5.model.DatabaseWatcher;
import assignment5.model.courseModel.*;
import assignment5.model.dataModel.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Controller handles input and output between front-end and back-end.
 * Has access to all content in model but accesses it through the mock database.
 */

@RestController
public class Controller {

    final Database database = Database.fromCSVData("data/course_data_2018.csv");
    final List<DatabaseWatcher> databaseWatchers = new ArrayList<>();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("api/about")
    public ApiAboutDTO getInfo() {
        return new ApiAboutDTO("Course Chaos (Assignment 5)", "Anureet Grewal");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("api/dump-model")
    public void dumpModel() {
        // Currently set to dump model in local terminal.
        database.display();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments")
    public List<ApiDepartmentDTO> getDepartments() {
        return database
                .getDepartments()
                .stream()
                .map(ApiDepartmentDTO::new)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{departmentID}/courses")
    public List<ApiCourseDTO> getCourses(@PathVariable("departmentID") int departmentID) {
        return database
                .getDepartments()
                .get(departmentID)
                .getCourses()
                .stream()
                .map(ApiCourseDTO::new)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{departmentID}/courses/{courseID}/offerings")
    public List<ApiCourseOfferingDTO> getOfferings(@PathVariable("departmentID") int departmentID,
                                                   @PathVariable("courseID") int courseID) {
        return database
                .getDepartments()
                .get(departmentID)
                .getCourses()
                .get(courseID)
                .getOfferings()
                .stream()
                .map(ApiCourseOfferingDTO::new)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{departmentID}/courses/{courseID}/offerings/{offeringID}")
    public List<ApiOfferingSectionDTO> getOfferingData(@PathVariable("departmentID") int departmentID,
                                                       @PathVariable("courseID") int courseID,
                                                       @PathVariable("offeringID") int offeringID) {
        return database
                .getDepartments()
                .get(departmentID)
                .getCourses()
                .get(courseID)
                .getOfferings()
                .get(offeringID)
                .getComponents()
                .stream().
                map(ApiOfferingSectionDTO::new)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/addoffering")
    public void addData(@RequestBody ApiOfferingDataDTO data) {

        // Handle odd input of instructor.
        List<String> instructors = new ArrayList<>();
        if (!data.instructor.isEmpty() && !data.instructor.isBlank()) {
            instructors.add(data.instructor);
        }

        // Add to database.
        database.addData(new DataCourse
                (data.semester, data.subjectName, data.catalogNumber, data.location,
                 data.enrollmentCap, data.enrollmentTotal, instructors, data.component));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/watchers")
    public List<ApiWatcherDTO> getWatchers() {
        return databaseWatchers.stream().map(ApiWatcherDTO::new).toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/watchers")
    public void addWatcher(@RequestBody ApiWatcherCreateDTO input) {
        CourseDepartment department = database.getDepartments().get((int) input.deptId);
        Course course = department.getCourses().get((int) input.courseId);
        DatabaseWatcher watcher = new DatabaseWatcher
                (databaseWatchers.size(), department.getID(), department.getSubject(),
                course.getID(), course.getCatalogNumber());
        course.addWatcher(watcher);
        databaseWatchers.add(watcher);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/watchers/{watcherID}")
    public ApiWatcherDTO getWatcher(@PathVariable("watcherID") int watcherID) {
        return new ApiWatcherDTO(databaseWatchers.get(watcherID));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/watchers/{watcherID}")
    public void removeWatcher(@PathVariable("watcherID") int watcherID) {
        databaseWatchers.remove(watcherID);
        for (int i = 0; i < databaseWatchers.size(); i++) {
            databaseWatchers.get(i).setID(i);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("api/stats/students-per-semester")
    public List<ApiGraphDataPointDTO> getGraphPoints(@RequestParam("deptId") int deptID) {
        assert(!database.isEmpty());
        List<String> semesters = DataSemesters.findSemesterRange
                (database.findEarliestSemester(), database.findLatestSemester());
        List<ApiGraphDataPointDTO> graphPoints = new ArrayList<>();

        List<CourseOffering> offerings = database.
                getDepartments()
                .get(deptID)
                .getCourses()
                .stream()
                .map(Course::getOfferings)
                .flatMap(List::stream)
                .toList();

        for (String semesterCode : semesters) {
            int totalTaken = offerings
                    .stream()
                    .filter(courseOffering -> courseOffering.getSemesterCode().equals(semesterCode))
                    .map(CourseOffering::getComponents)
                    .flatMap(List::stream)
                    .filter(courseComponent -> courseComponent.getComponentCode().equals("LEC"))
                    .mapToInt(CourseComponent::getEnrolmentTotal)
                    .sum();
            graphPoints.add(new ApiGraphDataPointDTO(Long.parseLong(semesterCode), totalTaken));
        }
        return graphPoints;
    }


}