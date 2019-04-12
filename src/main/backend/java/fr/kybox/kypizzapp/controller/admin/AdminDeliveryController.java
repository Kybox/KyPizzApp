package fr.kybox.kypizzapp.controller.admin;

import fr.kybox.kypizzapp.model.order.DeliveryMethod;
import fr.kybox.kypizzapp.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin/api")
public class AdminDeliveryController {

    private final DeliveryService deliveryService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public AdminDeliveryController(DeliveryService deliveryService) {

        this.deliveryService = deliveryService;
    }

    @PostMapping(value = "/delivery/",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeliveryMethod createNewDeliveryMethod(@RequestBody @Valid DeliveryMethod deliveryMethod) {

        log.info("Create new delivery method");
        return deliveryService.create(deliveryMethod);
    }

    @PutMapping(value = "/delivery/",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeliveryMethod updateDeliveryMethod(@RequestBody @Valid DeliveryMethod deliveryMethod){

        log.info("Update delivery method");
        return deliveryService.update(deliveryMethod);
    }

    @DeleteMapping(value = "/delivery/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<DeliveryMethod> deleteDeliveryMethod(@PathVariable String id) {

        log.info("Delete delivery method " + id);
        return deliveryService.delete(id);
    }
}
