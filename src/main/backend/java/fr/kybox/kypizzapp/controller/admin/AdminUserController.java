package fr.kybox.kypizzapp.controller.admin;

import fr.kybox.kypizzapp.model.GenericObject;
import fr.kybox.kypizzapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
public class AdminUserController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/count")
    public ResponseEntity<?> countAllUsers() {

        log.info("Count all users");
        return userService.countAllUsers();
    }

    @PostMapping(value = "/user/search",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> searchUser(@RequestBody GenericObject genericObject){

        log.info("Search user");
        return userService.searchUser(genericObject);
    }
}
