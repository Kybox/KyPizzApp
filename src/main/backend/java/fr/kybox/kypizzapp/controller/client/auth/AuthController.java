package fr.kybox.kypizzapp.controller.client.auth;

import fr.kybox.kypizzapp.model.auth.*;
import fr.kybox.kypizzapp.security.jwt.model.JwtToken;
import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtProperties jwtProperties;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, JwtProperties jwtProperties) {
        this.authService = authService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RegisteredUser> registerUser(@RequestBody @Valid RegisterForm registerForm){

        log.info(registerForm.toString());
        return ResponseEntity.ok().body(authService.registerUser(registerForm));
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<JwtToken> loginUser(@RequestBody @Valid LoginForm loginForm) {

        log.info(loginForm.toString());

        JwtToken jwtToken = authService.loginUser(loginForm);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(jwtProperties.getHeader(),
                jwtProperties.getPrefix() + " " + jwtToken.getToken());

        return new ResponseEntity<>(jwtToken, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Authenticated> isAuthenticated(HttpServletRequest request){

        log.info("IsAuthenticated");
        log.info("Header : " + request.getHeader(jwtProperties.getHeader()));
        return ResponseEntity.ok(authService.isAuthenticated(request));
    }

    @GetMapping(value = "/storage/key")
    public ResponseEntity<JwtStorageKey> getStorageKey() {

        log.info("Get storage key");
        return ResponseEntity.ok(new JwtStorageKey(jwtProperties.getStorageKey()));
    }
}
