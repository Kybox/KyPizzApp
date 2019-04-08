package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.model.order.Order;
import fr.kybox.kypizzapp.model.order.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByCustomerAndStatus(RegisteredUser user, OrderStatus orderStatus);
}
