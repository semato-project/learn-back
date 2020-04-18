package semato.semato_learn.controller.payload;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@ApiModel
public class AuthRequest {
    @NotBlank
    @ApiModelProperty(example = "profesordoktor@example.com")
    String email;

    @NotBlank
    @ApiModelProperty(example = "qwerty")
    String password;
}
