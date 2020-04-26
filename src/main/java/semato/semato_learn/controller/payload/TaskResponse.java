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
public class TaskResponse {

    private long id;
    private TaskType taskType;
    private int quantity;

    public TaskResponse(Task task) {
        id = task.getId();
        taskType = task.getTaskType();
        quantity = task.getQuantity();
    }
}
