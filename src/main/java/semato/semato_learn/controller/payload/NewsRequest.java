package semato.semato_learn.controller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@ApiModel
public class NewsRequest {

    @NotBlank
    @ApiModelProperty(example = "1")
    private Long lecturerId;

    @NotBlank
    @ApiModelProperty(example = "Zagrożenie III stopnia")
    private String title;

    @NotBlank
    @ApiModelProperty(example = "Zagrożenie dotyczy...")
    private String description;

}
