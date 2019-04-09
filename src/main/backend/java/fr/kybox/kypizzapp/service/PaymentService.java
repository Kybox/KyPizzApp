package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.payment.Payment;

import java.util.List;

public interface PaymentService {

    Payment findById(String id);
    List<Payment> findAll();
    Payment create(Payment payment);
    Payment update(Payment payment);
}
