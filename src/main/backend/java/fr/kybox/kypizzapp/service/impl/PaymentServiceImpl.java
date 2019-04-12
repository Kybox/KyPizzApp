package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.BadRequestException;
import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.model.payment.Payment;
import fr.kybox.kypizzapp.repository.PaymentRepository;
import fr.kybox.kypizzapp.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment findById(String id) {

        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The payment method was not found"));
    }

    @Override
    public List<Payment> findAll() {

        return paymentRepository.findAll();
    }

    @Override
    public Payment create(Payment payment) {

        return paymentRepository.save(payment);
    }

    @Override
    public Payment update(Payment payment) {

        if(!paymentRepository.findById(payment.getId()).isPresent())
            throw new BadRequestException("The payment object don't exists.");

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> delete(String id) {

        Optional<Payment> optPayment = paymentRepository.findById(id);
        if(!optPayment.isPresent())
            throw new NotFoundException("The payment method was not found.");

        paymentRepository.delete(optPayment.get());

        return paymentRepository.findAll();
    }
}
