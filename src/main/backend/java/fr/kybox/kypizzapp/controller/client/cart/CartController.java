package fr.kybox.kypizzapp.controller.client.cart;

import fr.kybox.kypizzapp.config.property.CookieProperties;
import fr.kybox.kypizzapp.model.cart.CartKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CookieProperties cookieProperties;

    @Autowired
    public CartController(CookieProperties cookieProperties) {
        this.cookieProperties = cookieProperties;
    }

    @GetMapping(value = "/cart/key", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CartKey> getCartKey() {

        log.info("Get cart key");
        return ResponseEntity.ok(new CartKey(cookieProperties.getKey()));
    }
}
