package fr.kybox.kypizzapp.controller.client.account;

import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping(value = "/user/details", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RegisteredUser> getUserDetails() {

        return null;
    }
}
