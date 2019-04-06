package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.GenericObject;
import fr.kybox.kypizzapp.model.cart.ProductFromCart;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {

    ResponseEntity<GenericObject> getCookieKey();
    ResponseEntity<GenericObject> getCart(HttpServletRequest request);
    ResponseEntity<Cookie> addProduct(ProductFromCart cartProduct, HttpServletRequest request, HttpServletResponse response);
}
