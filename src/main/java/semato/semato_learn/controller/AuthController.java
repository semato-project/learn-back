package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import semato.semato_learn.service.AuthService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    @ApiOperation(value = "Endpoint to authenticate user and generate jwt token")
    public ResponseEntity<?> authenticateUser(
        @ApiParam(example = "test@test.pl") @NotBlank @RequestParam String email,
        @ApiParam(example = "tajneHaslo") @NotBlank @RequestParam String password
    ) {
        authService.authenticateUser(email, password);
        String accessToken = authService.generateToken();
        return ResponseEntity.ok(accessToken);
    }
}