package fr.kybox.kypizzapp.controller.client;

import fr.kybox.kypizzapp.model.auth.Address;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account/api")
public class AccountController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/user/details", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RegisteredUser> getUserDetails() {

        return null;
    }

    @GetMapping(value = "/addresses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Address> getUserAddressList(HttpServletRequest req) {

        log.info("Get user addresses");
        return accountService.findUserAddressList(req);
    }

    @PostMapping(value = "/address",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address createNewAddress(@RequestBody @Valid Address address, HttpServletRequest req) {

        log.info("Create new address");
        return accountService.createNewAddress(address, req);
    }
}
