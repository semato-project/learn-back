package semato.semato_learn.controller.payload;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.TaskType;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class TaskRequest {

    @NotBlank
    TaskType taskType;

    @NotBlank
    int quantity;

    @NotBlank
    Double markWeight;

    int maxGroupQuantity;
}
