package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.config.property.CookieProperties;
import fr.kybox.kypizzapp.model.GenericObject;
import fr.kybox.kypizzapp.model.cart.Cart;
import fr.kybox.kypizzapp.model.cart.ProductFromCart;
import fr.kybox.kypizzapp.service.CookieService;
import fr.kybox.kypizzapp.utils.CookieUtils;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CookieServiceImpl implements CookieService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CookieProperties cookieProperties;

    @Autowired
    public CookieServiceImpl(CookieProperties cookieProperties) {
        this.cookieProperties = cookieProperties;
    }

    @Override
    public ResponseEntity<GenericObject> getCookieKey() {

        return ResponseEntity.ok(new GenericObject(cookieProperties.getKey()));
    }

    @Override
    public ResponseEntity<GenericObject> getCart(HttpServletRequest request) {

        Optional<Cookie> optCookie = getCookieFromRequest(request);
        return optCookie
                .map(cookie -> ResponseEntity.ok().body(new GenericObject(cookie.getValue())))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Override
    public ResponseEntity<Cookie> addProduct(ProductFromCart cartProduct, HttpServletRequest request, HttpServletResponse response) {

        Cart cart = null;
        Cookie cookie = null;
        Cookie[] cookieList = request.getCookies();

        Optional<Cookie> optCookie;

        if(cookieList != null){
            optCookie = Arrays
                    .stream(cookieList)
                    .filter(c -> c.getName().equals(cookieProperties.getKey()))
                    .findFirst();
        }
        else {
            log.info("NO COOKIES");
            optCookie = Optional.empty();
        }

        if(optCookie.isPresent()){

            cookie = optCookie.get();
            cart = CookieUtils.getCartFromCookie(cookie);

            log.info("Cart from cookie : " + cart);

            boolean productExist = cart.getProductList()
                    .stream()
                    .anyMatch(p -> p.getId().equals(cartProduct.getId()));

            if(productExist) {
                cart.getProductList()
                        .stream()
                        .filter(p -> p.getId().equals(cartProduct.getId()))
                        .findFirst().ifPresent(p -> p.setQuantity(p.getQuantity() + cartProduct.getQuantity()));
            }
            else cart.getProductList().add(cartProduct);
        }
        else {

            cart = new Cart();
            cart.getProductList().add(cartProduct);
        }

        response.addCookie(CookieUtils.getCookieFromCart(cart));

        return ResponseEntity.ok(cookie);
    }

    private Optional<Cookie> getCookieFromRequest(HttpServletRequest request){

        Cookie[] cookieList = request.getCookies();

        if(cookieList == null) return Optional.empty();

        return Arrays
                .stream(cookieList)
                .filter(c -> c.getName().equals(cookieProperties.getKey()))
                .findFirst();
    }
}
