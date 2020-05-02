package semato.semato_learn.controller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel
public class NewsEditRequest {

    @ApiModelProperty(example = "Zagrożenie III stopnia")
    private String title;

    @ApiModelProperty(example = "Zagrożenie dotyczy...")
    private String description;

}
