package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class TaskRequest {

    @NotBlank
    TaskType taskType;

    @NotBlank
    int quantity;

    @NotBlank
    Double markWage;

    int maxGroupQuantity;
}
