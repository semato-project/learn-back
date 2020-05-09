package semato.semato_learn.controller.payload;

import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import semato.semato_learn.model.ProjectGroup;
import semato.semato_learn.model.Student;

import java.beans.Transient;
import java.time.Instant;
import java.util.LinkedList;

@Getter
public class ProjectGroupResponse {

    private long projectGroupId;
    private long taskId;
    private long maxGroupQuantity;
    private LinkedList<StudentResponse> studentResponseList = new LinkedList<StudentResponse>();

    @JsonIgnore
    private Instant createdAt;

    public ProjectGroupResponse(ProjectGroup projectGroup) {
        projectGroupId = projectGroup.getId();
        taskId = projectGroup.getTask().getId();
        maxGroupQuantity = projectGroup.getTask().getMaxGroupQuantity();
        for (Student student: projectGroup.getStudents()) {
            studentResponseList.add(new StudentResponse(student));
        }
        this.createdAt = projectGroup.getCreatedAt();
    }


}
