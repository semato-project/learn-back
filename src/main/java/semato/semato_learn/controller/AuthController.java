package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semato.semato_learn.controller.payload.AuthRequest;
import semato.semato_learn.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    @ApiOperation(value = "Endpoint to authenticate user and generate jwt token")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authParam) {
        authService.authenticateUser(authParam.getEmail(), authParam.getPassword());
        String accessToken = authService.generateToken();
        return ResponseEntity.ok(accessToken);
    }
}