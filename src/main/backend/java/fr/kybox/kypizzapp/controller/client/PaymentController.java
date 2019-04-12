package fr.kybox.kypizzapp.controller.client;

import com.paypal.api.payments.RedirectUrls;
import fr.kybox.kypizzapp.model.payment.paypal.PaypalPayUrl;
import fr.kybox.kypizzapp.service.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/payment/api")
public class PaymentController {

    private final PaypalService paypalService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public PaymentController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping(value = "/paypal/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaypalPayUrl payOrder(@PathVariable String id,
                                 @RequestBody @Valid RedirectUrls redirectUrls)
            throws IOException {

        log.info("Pay order with Paypal");
        log.info("Id : " + id);
        log.info("Url : " + redirectUrls);
        //return null;
        return paypalService.paymentProcess(id, redirectUrls);
    }
}
