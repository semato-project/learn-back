package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.TaskType;

@Getter
public class GradeResponse {

    private long id;
    private Double gradeValue;
    private TaskType taskType;
    private int taskNumber;

    public GradeResponse(Grade grade) {
        id = grade.getId();
        taskType = grade.getTask().getTaskType();
        taskNumber = grade.getTaskNumber();
        gradeValue = grade.getGradeValue();
    }

}
