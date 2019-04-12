package fr.kybox.kypizzapp.service;

import com.paypal.api.payments.Item;
import com.paypal.api.payments.RedirectUrls;
import fr.kybox.kypizzapp.model.order.Order;
import fr.kybox.kypizzapp.model.payment.paypal.PaypalPayUrl;

import java.io.IOException;
import java.util.List;

public interface PaypalService {

    PaypalPayUrl paymentProcess(String orderId, RedirectUrls redirectUrls) throws IOException;
}
