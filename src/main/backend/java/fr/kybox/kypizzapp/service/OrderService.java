package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.exception.BadRequestException;
import fr.kybox.kypizzapp.model.order.Order;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    Order createOrder(HttpServletRequest request);
    Order updateOrder(Order order) throws BadRequestException;
    Order findLastSavedOrder(HttpServletRequest request);
    Order findById(String id);
}
