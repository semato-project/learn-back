package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.Group;

import javax.validation.constraints.NotBlank;
import java.util.LinkedList;

@Getter
@AllArgsConstructor
public class CourseRequest {

    @NotBlank
    Long groupId;

    @NotBlank
    String name;

    String description;

    LinkedList<TaskRequest> taskList;
}
