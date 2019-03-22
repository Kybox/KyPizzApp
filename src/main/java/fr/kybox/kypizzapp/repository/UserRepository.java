package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
