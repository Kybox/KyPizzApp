package fr.kybox.kypizzapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kybox.kypizzapp.config.property.CookieProperties;
import fr.kybox.kypizzapp.model.cart.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtils {

    private static CookieProperties cookieProperties;

    private static Logger log = LoggerFactory.getLogger(CookieUtils.class);

    @Autowired
    public CookieUtils(CookieProperties cookieProperties) {
        CookieUtils.cookieProperties = cookieProperties;
    }

    public static Cart getCartFromCookie(Cookie cookie) {

        Cart cart = null;

        try { cart = new ObjectMapper().readValue(cookie.getValue(), Cart.class); }
        catch (IOException e) { log.warn(e.getMessage()); }

        return cart;
    }

    public static Cookie getCookieFromCart(Cart cart) {

        log.info("Cart to cookie = " + cart);
        log.info("Cookie key = " + cookieProperties.getKey());
        String json = null;

        try { json = new ObjectMapper().writeValueAsString(cart); }
        catch (JsonProcessingException e) { log.warn(e.getMessage()); }

        Cookie cookie = new Cookie(cookieProperties.getKey(), json);
        cookie.setHttpOnly(false);
        cookie.setVersion(1);
        cookie.setMaxAge(60*60*24); // 24 hours
        cookie.setPath("/");

        return cookie;
    }

    public static Cookie getCookieCart(HttpServletRequest request) {

        Cookie[] cookieList = request.getCookies();

        Optional<Cookie> optCookie;

        if(cookieList != null){
            optCookie = Arrays
                    .stream(cookieList)
                    .filter(c -> c.getName().equals(cookieProperties.getKey()))
                    .findFirst();

            if(optCookie.isPresent()) return optCookie.get();

            else{
                log.warn("There are no cart in the cookies");
                return null;
            }
        }
        else {
            log.warn("There are no cookie");
            return null;
        }
    }
}
