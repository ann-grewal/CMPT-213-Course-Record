package assignment5.api;

import assignment5.model.courseModel.CourseDepartment;

public class ApiDepartmentDTO {
    public long deptId;
    public String name;

    public ApiDepartmentDTO (CourseDepartment department) {
        this.deptId = department.getID();
        this.name = department.getSubject();
    }

    public ApiDepartmentDTO(long deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }
}
