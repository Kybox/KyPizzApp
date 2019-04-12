package fr.kybox.kypizzapp.controller.client;

import fr.kybox.kypizzapp.model.order.Order;
import fr.kybox.kypizzapp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/order/api")
public class OrderController {

    private final OrderService orderService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order createNewOrder(HttpServletRequest request) {

        log.info("Create order");
        return orderService.createOrder(request);
    }

    @GetMapping(value = "/last/saved", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order getLastSavedOrder(HttpServletRequest request) {

        log.info("Get last saved order");
        return orderService.findLastSavedOrder(request);
    }

    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order updateOrderId(@PathVariable String id, @RequestBody @Valid Order order) {

        log.info("Save order " + id);
        return orderService.updateOrder(order);
    }

    @PutMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order updateOrder(@RequestBody @Valid Order order) {

        log.info("Update order");
        return orderService.updateOrder(order);
    }
}
