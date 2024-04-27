package assignment5.api;
import assignment5.model.DatabaseWatcher;

import java.util.List;

public class ApiWatcherDTO {
    public long id;
    public ApiDepartmentDTO department;
    public ApiCourseDTO course;
    public List<String> events;

    public ApiWatcherDTO(DatabaseWatcher watcher) {
        this.id = watcher.getID();
        this.department = new ApiDepartmentDTO(watcher.getDeptID(), watcher.getSubject());
        this.course = new ApiCourseDTO(watcher.getCourseID(), watcher.getCatalogNumber());
        this.events = watcher.getEvents();
    }
}
