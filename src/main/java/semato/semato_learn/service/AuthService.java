package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.LoginRequest;
import semato.semato_learn.util.security.JwtTokenProvider;

@Service
public class AuthService {

    private Authentication authentication;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public void authenticateUser(LoginRequest loginRequest){
        this.authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String generateToken(){
        return tokenProvider.generateToken(authentication);
    }
}
