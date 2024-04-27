package assignment5.model.courseModel;

import assignment5.model.DatabaseWatcher;
import assignment5.model.dataModel.DataCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Course holds all information about a particular course including the catalog number and semesters.
 * In charge of delegating construction, watching, and display of all offerings (continued chain).
 * Class relies on CourseOffering and DataCourse (always correct), and provides information to CourseWatchers.
 */

public class Course implements Comparable<Course> {
    private long ID = -1;
    private final String catalogNumber;
    private final List<CourseOffering> offerings = new ArrayList<>();
    private final List<DatabaseWatcher> watchers = new ArrayList<>();

    public Course(DataCourse data) {
        this.catalogNumber = data.catalogNumber();
        addOffering(data);
    }

    public void addOffering(DataCourse data) {
        CourseOffering offering = findOffering(data.semesterCode(), data.location());
        if (offering != null) {
            offering.addData(data);
        } else {
            offerings.add(new CourseOffering(data));
            offering = offerings.getLast();
        }
        for (DatabaseWatcher watcher: watchers) {
            watcher.addEvent(offering.getLastUpdate());
        }
    }

    private CourseOffering findOffering(String semesterCode, String location) {
        for (CourseOffering offering : offerings) {
            boolean equalSemesterCode = offering.getSemesterCode().equals(semesterCode);
            boolean equalLocation = offering.getLocation().equals(location);
            if (equalSemesterCode && equalLocation) {
                return offering;
            }
        }
        return null;
    }

    public void setIDs(long ID) {
        this.ID = ID;
        sortLists();
        for (int i = 0; i < offerings.size(); i++) {
            offerings.get(i).setIDs(i);
        }
    }

    public void resetID(long ID) {
        this.ID = ID;
    }

    public void resetInternalIDs(DataCourse data) {
        CourseOffering offering = findOffering(data.semesterCode(), data.location());
        assert (offering != null);

        if (offering.getID() == -1) {
            sortLists();
            for (int i = 0; i < offerings.size(); i++) {
                offerings.get(i).resetID(i);
            }
        }
        offering.resetInternalIDs();
    }

    public void display(String subject) {
        sortLists();
        System.out.println(subject + " " + catalogNumber);
        for (CourseOffering offering : offerings) {
            offering.display();
        }
    }

    public void addWatcher(DatabaseWatcher watcher) {
        watchers.add(watcher);
    }

    public long getID() {
        return ID;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<CourseOffering> getOfferings() {
        return offerings;
    }

    public void sortLists() {
        offerings.sort(null);
    }

    @Override
    public int compareTo(Course other) {
        return this.catalogNumber.compareTo(other.catalogNumber);
    }
}
