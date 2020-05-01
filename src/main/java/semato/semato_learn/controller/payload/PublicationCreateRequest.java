package semato.semato_learn.controller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@ApiModel
public class PublicationCreateRequest {

    @NotBlank
    @ApiModelProperty(example = "Jaki 5G ma na nas wpływ...")
    private String title;

    @NotBlank
    @ApiModelProperty(example = "Opis publikacji")
    private String description;

}
