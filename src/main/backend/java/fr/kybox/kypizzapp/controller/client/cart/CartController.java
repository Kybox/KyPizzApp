package fr.kybox.kypizzapp.controller.client.cart;

import fr.kybox.kypizzapp.model.GenericObject;
import fr.kybox.kypizzapp.model.cart.Cart;
import fr.kybox.kypizzapp.model.cart.ProductFromCart;
import fr.kybox.kypizzapp.service.CookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CartController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CookieService cookieService;

    @Autowired
    public CartController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @GetMapping(value = "/cart/key", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GenericObject> getCartKey() {

        log.info("Get cart key");
        return cookieService.getCookieKey();
    }

    @GetMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GenericObject> getCart(HttpServletRequest request){

        log.info("Get cart");
        return cookieService.getCart(request);
    }

    @PostMapping(value = "/cart/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getProductToCart(
            @RequestBody @Valid ProductFromCart cartProduct,
            HttpServletRequest request, HttpServletResponse response) {

        log.info("Product : " + cartProduct);
        return cookieService.addProduct(cartProduct, request, response);
    }

    @PostMapping(value = "/cart/update",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Cart updateCart(@RequestBody @Valid Cart cart,
                           HttpServletRequest req,
                           HttpServletResponse resp) {

        log.info("Update cart");
        return cookieService.updateCart(cart, req, resp);
    }
}
