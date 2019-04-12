package fr.kybox.kypizzapp.controller.client;

import fr.kybox.kypizzapp.model.order.DeliveryMethod;
import fr.kybox.kypizzapp.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping(value = "/delivery/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<DeliveryMethod> getDeliveryMethodList() {

        log.info("Get delivery method list");
        return deliveryService.findAll();
    }

    @GetMapping(value = "/delivery/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeliveryMethod getDeliveryMethodById(@PathVariable String id) {

        log.info("Get delivery method by id " + id);
        return deliveryService.findById(id);
    }
}
