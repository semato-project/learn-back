package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.TaskType;

@Getter
@AllArgsConstructor
public class TaskRequest {
    TaskType taskType;
    int quantity;
    Double markWage;
    int maxGroupQuantity;
}
