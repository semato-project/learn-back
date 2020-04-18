package semato.semato_learn.controller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@ApiModel
public class GradeRequest {

    @NotBlank
    @ApiModelProperty(example = "1")
    Long studentId;

    @NotBlank
    @ApiModelProperty(example = "1")
    Long taskId;

    @NotBlank
    @ApiModelProperty(example = "1")
    int taskNumber;

    @NotBlank
    @ApiModelProperty(example = "4.5")
    double grade;
}
