package semato.semato_learn.controller.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.Task;
import semato.semato_learn.model.TaskType;
import semato.semato_learn.service.GradeManagerService;

import java.util.LinkedList;
import java.util.Set;

@Getter
@NoArgsConstructor
public class CourseExtendedResponse {

    private long courseId;

    private String name;

    private String description;

    private GroupResponse group;

    private LinkedList<TaskResponse> taskList = new LinkedList<TaskResponse>();

    private LinkedList<CourseParticipantResponse> participantList = new LinkedList<CourseParticipantResponse>();

    public CourseExtendedResponse(Course course, Set<Student> studentList, GradeManagerService gradeManagerService) {
        courseId = course.getId();
        name = course.getName();
        description = course.getDescription();
        group = GroupResponse.create(course.getGroup());
        group.setStudentIds(null);
        group.setCourseIds(null);

        for (TaskType taskType: TaskType.values()) {
            for (Task task: course.getTasks()) {
                if (task.getTaskType() == taskType) {
                    taskList.add(new TaskResponse(task));
                }
            }
        }

        for (Student student: studentList) {
            participantList.add(new CourseParticipantResponse(student, course, gradeManagerService));
        }
    }
}
