package fr.kybox.kypizzapp.controller.client.auth;

import fr.kybox.kypizzapp.model.auth.LoginForm;
import fr.kybox.kypizzapp.model.auth.RegisterForm;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/shop/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RegisteredUser> registerUser(@RequestBody @Valid RegisterForm registerForm){

        return ResponseEntity.ok().body(authService.registerUser(registerForm));
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RegisteredUser> loginUser(@RequestBody @Valid LoginForm loginForm) {

        return ResponseEntity.ok().body(authService.loginUser(loginForm));
    }
}
