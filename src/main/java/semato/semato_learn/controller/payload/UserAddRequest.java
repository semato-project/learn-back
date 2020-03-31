package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAddRequest {

    String email;

    String firstName;

    String lastName;

    String password;
}
