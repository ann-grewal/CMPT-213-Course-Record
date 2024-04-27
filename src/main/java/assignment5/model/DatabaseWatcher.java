package assignment5.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 * Class DatabaseWatcher logs changes for a certain course with updates provided by Course class.
 */

public class DatabaseWatcher {
    private long ID;
    private final long deptID;
    private final String subject;
    private final long courseID;
    private final String catalogNumber;
    private final List<String> events = new ArrayList<>();

    public DatabaseWatcher(long ID, long deptID, String subject, long courseID, String catalogNumber) {
        this.ID = ID;
        this.deptID = deptID;
        this.subject = subject;
        this.courseID = courseID;
        this.catalogNumber = catalogNumber;
    }

    public void addEvent(String event) {
        Date date = new java.util.Date();
        events.add(date + " " + event);
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public long getDeptID() {
        return deptID;
    }

    public String getSubject() {
        return subject;
    }

    public long getCourseID() {
        return courseID;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<String> getEvents() {
        return events;
    }
}
