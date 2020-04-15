package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@AllArgsConstructor
public class CourseRequest {

    @NotBlank
    Long groupId;

    @NotBlank
    String name;

    String description;

    List<TaskRequest> taskList;
}
