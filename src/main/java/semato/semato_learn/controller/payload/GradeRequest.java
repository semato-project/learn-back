package semato.semato_learn.controller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class GradeRequest {

    @NotBlank
    @ApiModelProperty(example = "1")
    Long id;

    @NotBlank
    @ApiModelProperty(example = "4.5")
    double grade;
}
