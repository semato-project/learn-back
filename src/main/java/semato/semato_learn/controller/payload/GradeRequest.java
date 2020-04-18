package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class GradeRequest {

    @NotBlank
    private long studentId;

    @NotBlank
    private long taskId;

    @NotBlank
    private int taskNumber;

    @NotBlank
    private double grade;
}
