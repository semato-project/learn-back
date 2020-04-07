package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.Group;

import java.util.LinkedList;

@Getter
@AllArgsConstructor
public class CourseRequest {
    Long groupId;
    String name;
    String description;
    LinkedList<TaskRequest> taskList;
}
