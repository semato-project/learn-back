package semato.semato_learn.controller.payload;

import lombok.Getter;
import semato.semato_learn.model.ProjectGroup;
import semato.semato_learn.model.Student;
import java.util.LinkedList;

@Getter
public class ProjectGroupResponse {

    private long projectGroupId;
    private long taskId;
    private LinkedList<StudentResponse> studentResponseList = new LinkedList<StudentResponse>();

    public ProjectGroupResponse(ProjectGroup projectGroup) {
        projectGroupId = projectGroup.getId();
        taskId = projectGroup.getTask().getId();
        for (Student student: projectGroup.getStudents()) {
            studentResponseList.add(new StudentResponse(student));
        }
    }


}
