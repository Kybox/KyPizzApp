package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.payment.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
