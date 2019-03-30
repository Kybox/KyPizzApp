package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends MongoRepository<RegisteredUser, String> {

    Optional<RegisteredUser> findFirstByEmail(String email);
    Optional<RegisteredUser> findFirstByNickNameIgnoreCase(String nickName);
}
