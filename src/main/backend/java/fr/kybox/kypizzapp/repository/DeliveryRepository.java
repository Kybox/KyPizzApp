package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.order.DeliveryMethod;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryRepository extends MongoRepository<DeliveryMethod, String> {
}
