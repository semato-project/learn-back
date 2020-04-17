package semato.semato_learn.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semato.semato_learn.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    @ApiOperation(value = "Endpoint to authenticate user and generate jwt token")
    public ResponseEntity<?> authenticateUser(@RequestBody final AuthParam authParam) {
        authService.authenticateUser(authParam.email, authParam.password);
        String accessToken = authService.generateToken();
        return ResponseEntity.ok(accessToken);
    }

    @AllArgsConstructor
    private static final class AuthParam {
        final String email;
        final String password;
    }
}