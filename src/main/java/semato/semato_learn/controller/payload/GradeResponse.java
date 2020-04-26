package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Student;

@Getter
public class GradeResponse {

    private long id;
    private double gradeValue;

    public GradeResponse(Grade grade) {
        id = grade.getId();
        gradeValue = grade.getGradeValue();
    }

}
