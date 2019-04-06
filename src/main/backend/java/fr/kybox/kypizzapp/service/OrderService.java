package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.order.Order;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    ResponseEntity<Order> createOrder(HttpServletRequest request);
}
