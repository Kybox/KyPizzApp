package fr.kybox.kypizzapp.controller.admin;

import fr.kybox.kypizzapp.model.payment.Payment;
import fr.kybox.kypizzapp.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminPaymentController {

    private final PaymentService paymentService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public AdminPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "payment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment findById(@PathVariable String id) {

        log.info("Find payment by id");
        return paymentService.findById(id);
    }

    @GetMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Payment> findAll() {

        log.info("Get payment list");
        return paymentService.findAll();
    }

    @PostMapping(value = "/payment",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment create(@RequestBody @Valid Payment payment) {

        log.info("Create payment");
        return paymentService.create(payment);
    }

    @PutMapping(value = "/payment",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment update(@RequestBody @Valid Payment payment) {

        log.info("Update payment");
        return paymentService.update(payment);
    }

    @DeleteMapping(value = "/payment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Payment> delete(@PathVariable String id) {

        log.info("Delete payment " + id);
        return paymentService.delete(id);
    }
}
